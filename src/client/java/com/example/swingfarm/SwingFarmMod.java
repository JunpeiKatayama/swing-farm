package com.example.swingfarm;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

public class SwingFarmMod implements ClientModInitializer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger("SwingFarm");
    private static final int ATTACK_RANGE = 3;
    private static final int ATTACK_COOLDOWN = 20; // 20 ticks = 1 second
    
    private int attackCooldownTimer = 0;
    private boolean isEnabled = false; // MODの有効/無効状態
    
    // キーバインド設定
    private static KeyBinding toggleKeyBinding;
    
    @Override
    public void onInitializeClient() {
        // キーバインドを登録 (F8キー)
        toggleKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.swingfarm.toggle", // 翻訳キー
            InputUtil.Type.KEYSYM,
            InputUtil.GLFW_KEY_F8, // F8キー
            "category.swingfarm" // カテゴリ
        ));
        
        // クライアントティックイベントを登録
        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
    }
    
    private void onClientTick(MinecraftClient client) {
        // キーバインドの状態をチェック（F8キー）
        while (toggleKeyBinding.wasPressed()) {
            isEnabled = !isEnabled;
            LOGGER.info("SwingFarm toggled: {}", isEnabled ? "ENABLED" : "DISABLED");
            if (client.player != null) {
                String status = isEnabled ? "有効" : "無効";
                client.player.sendMessage(
                    Text.literal("§6[SwingFarm] §f自動攻撃機能: §" + (isEnabled ? "a" : "c") + status),
                    false
                );
            }
        }
        
        // MODが無効の場合は何もしない
        if (!isEnabled) {
            return;
        }
        
        // クールダウンタイマーを減らす
        if (attackCooldownTimer > 0) {
            attackCooldownTimer--;
        }
        
        // 基本的な条件をチェック
        if (!canPerformAutoAttack(client)) {
            return;
        }
        
        ClientPlayerEntity player = client.player;
        World world = player.getWorld();
        
        // プレイヤーの前方3ブロック以内の敵対Mobを検索
        MobEntity targetMob = findNearestHostileMob(player, world);
        
        if (targetMob != null && canSeeEntity(player, targetMob)) {
            // 攻撃実行
            performAttack(client, player, targetMob);
        }
    }
    
    /**
     * 自動攻撃が可能かどうかチェック
     */
    private boolean canPerformAutoAttack(MinecraftClient client) {
        // クライアントやプレイヤーが存在しない場合
        if (client == null || client.player == null || client.world == null) {
            return false;
        }
        
        ClientPlayerEntity player = client.player;
        
        // プレイヤーが生存していない場合
        if (!player.isAlive()) {
            return false;
        }
        
        // メインハンドに剣を装備していない場合
        if (!player.getMainHandStack().isIn(ItemTags.SWORDS)) {
            return false;
        }
        
        // 攻撃クールダウン中の場合
        if (attackCooldownTimer > 0) {
            return false;
        }
        
        return true;
    }
    
    /**
     * プレイヤーの前方3ブロック以内の最も近い敵対Mobを検索
     */
    private MobEntity findNearestHostileMob(PlayerEntity player, World world) {
        Vec3d playerPos = player.getPos();
        Vec3d lookDirection = player.getRotationVector();
        
        // プレイヤーの前方3ブロック以内のボックスを作成
        Box searchBox = new Box(
            playerPos.x - ATTACK_RANGE, playerPos.y - 1, playerPos.z - ATTACK_RANGE,
            playerPos.x + ATTACK_RANGE, playerPos.y + 2, playerPos.z + ATTACK_RANGE
        );
        
        List<Entity> entities = world.getOtherEntities(player, searchBox);
        
        MobEntity nearestMob = null;
        double nearestDistance = Double.MAX_VALUE;
        
        for (Entity entity : entities) {
            if (!(entity instanceof MobEntity mobEntity)) {
                continue;
            }
            
            // 敵対的でない場合はスキップ
            if (!isHostileToPlayer(mobEntity, player)) {
                continue;
            }
            
            // プレイヤーとの距離をチェック
            double distance = playerPos.distanceTo(entity.getPos());
            if (distance <= ATTACK_RANGE && distance < nearestDistance) {
                // プレイヤーの前方にいるかチェック
                Vec3d toEntity = entity.getPos().subtract(playerPos).normalize();
                double dotProduct = lookDirection.dotProduct(toEntity);
                
                // 前方約90度以内にいる場合（dotProduct > 0.3）
                if (dotProduct > 0.3) {
                    nearestMob = mobEntity;
                    nearestDistance = distance;
                }
            }
        }
        
        return nearestMob;
    }
    
    /**
     * Mobがプレイヤーに対して敵対的かどうかチェック
     */
    private boolean isHostileToPlayer(MobEntity mobEntity, PlayerEntity player) {
        // 死んでいる場合は攻撃対象外
        if (!mobEntity.isAlive()) {
            return false;
        }
        
        // MobEntityのターゲットがプレイヤーかどうかをチェック
        LivingEntity target = mobEntity.getTarget();
        if (target == player) {
            return true;
        }
        
        // HostileEntityクラスのインスタンスかどうかをチェック
        if (mobEntity instanceof HostileEntity) {
            return true;
        }
        
        // その他の敵対的なMobかどうかをチェック
        // プレイヤーをターゲットにしている場合は敵対的とみなす
        return mobEntity.getTarget() instanceof PlayerEntity;
    }
    
    /**
     * プレイヤーがエンティティを見ることができるかチェック
     */
    private boolean canSeeEntity(PlayerEntity player, Entity target) {
        Vec3d playerEyePos = player.getEyePos();
        Vec3d targetPos = target.getPos().add(0, target.getHeight() / 2, 0);
        
        // レイキャストを使用して視線が通っているかチェック
        RaycastContext context = new RaycastContext(
            playerEyePos, 
            targetPos, 
            RaycastContext.ShapeType.COLLIDER, 
            RaycastContext.FluidHandling.NONE, 
            player
        );
        
        HitResult hitResult = player.getWorld().raycast(context);
        
        // ブロックに当たらない、または対象エンティティに当たる場合は見える
        return hitResult.getType() == HitResult.Type.MISS || 
               (hitResult instanceof EntityHitResult entityHit && entityHit.getEntity() == target);
    }
    
    /**
     * 攻撃を実行
     */
    private void performAttack(MinecraftClient client, ClientPlayerEntity player, MobEntity target) {
        // 攻撃実行
        client.interactionManager.attackEntity(player, target);
        
        // 剣を振る
        player.swingHand(Hand.MAIN_HAND);
        
        // クールダウンタイマーをリセット
        attackCooldownTimer = ATTACK_COOLDOWN;
    }
} 