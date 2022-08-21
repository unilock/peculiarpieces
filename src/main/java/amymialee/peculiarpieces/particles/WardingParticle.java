package amymialee.peculiarpieces.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

public class WardingParticle extends Particle {
    protected Sprite sprite;

    protected WardingParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        super(clientWorld, d, e, f, g, h, i);
        this.velocityMultiplier = 0.00f;
        this.maxAge = 24;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3d vec3d = camera.getPos();
        float l = this.sprite.getMinU();
        float m = this.sprite.getMaxU();
        float n = this.sprite.getMinV();
        float o = this.sprite.getMaxV();
        for (Direction direction : Direction.values()) {
            float f = (float)(MathHelper.lerp(tickDelta, this.prevPosX, this.x) - vec3d.getX());
            float g = (float)(MathHelper.lerp(tickDelta, this.prevPosY, this.y) - vec3d.getY());
            float h = (float)(MathHelper.lerp(tickDelta, this.prevPosZ, this.z) - vec3d.getZ());
            Vec3f[] vec3set = directionVectors(direction);
            for (int k = 0; k < 4; k++) {
                Vec3f vec3f2 = vec3set[k];
                vec3f2.scale(0.501f);
                vec3f2.add(f, g, h);
            }
            vertexConsumer.vertex(vec3set[0].getX(), vec3set[0].getY(), vec3set[0].getZ()).texture(m, o).color(this.red, this.green, this.blue, this.alpha).light(LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE).next();
            vertexConsumer.vertex(vec3set[1].getX(), vec3set[1].getY(), vec3set[1].getZ()).texture(m, n).color(this.red, this.green, this.blue, this.alpha).light(LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE).next();
            vertexConsumer.vertex(vec3set[2].getX(), vec3set[2].getY(), vec3set[2].getZ()).texture(l, n).color(this.red, this.green, this.blue, this.alpha).light(LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE).next();
            vertexConsumer.vertex(vec3set[3].getX(), vec3set[3].getY(), vec3set[3].getZ()).texture(l, o).color(this.red, this.green, this.blue, this.alpha).light(LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE).next();
        }
    }

    private static Vec3f[] directionVectors(Direction direction) {
        return switch (direction) {
            case UP -> new Vec3f[]{new Vec3f(1.0f, -1.0f, -1.0f), new Vec3f(1.0f, -1.0f, 1.0f), new Vec3f(-1.0f, -1.0f, 1.0f), new Vec3f(-1.0f, -1.0f, -1.0f)};
            case DOWN -> new Vec3f[]{new Vec3f(-1.0f, 1.0f, -1.0f), new Vec3f(-1.0f, 1.0f, 1.0f), new Vec3f(1.0f, 1.0f, 1.0f), new Vec3f(1.0f, 1.0f, -1.0f)};
            case EAST -> new Vec3f[]{new Vec3f(-1.0f, -1.0f, 1.0f), new Vec3f(-1.0f, 1.0f, 1.0f), new Vec3f(-1.0f, 1.0f, -1.0f), new Vec3f(-1.0f, -1.0f, -1.0f)};
            case WEST -> new Vec3f[]{new Vec3f(1.0f, -1.0f, 1.0f), new Vec3f(1.0f, -1.0f, -1.0f), new Vec3f(1.0f, 1.0f, -1.0f), new Vec3f(1.0f, 1.0f, 1.0f)};
            case SOUTH -> new Vec3f[]{new Vec3f(-1.0f, -1.0f, -1.0f), new Vec3f(-1.0f, 1.0f, -1.0f), new Vec3f(1.0f, 1.0f, -1.0f), new Vec3f(1.0f, -1.0f, -1.0f)};
            case NORTH -> new Vec3f[]{new Vec3f(-1.0f, -1.0f, 1.0f), new Vec3f(1.0f, -1.0f, 1.0f), new Vec3f(1.0f, 1.0f, 1.0f), new Vec3f(-1.0f, 1.0f, 1.0f)};
        };
    }

    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setSprite(SpriteProvider spriteProvider) {
        this.setSprite(spriteProvider.getSprite(this.random));
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void move(double dx, double dy, double dz) {}

    @Override
    public int getBrightness(float tint) {
        return 240;
    }

    @Environment(value = EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            WardingParticle wardingParticle = new WardingParticle(clientWorld, d, e, f, g, h, i);
            wardingParticle.setSprite(this.spriteProvider);
            return wardingParticle;
        }
    }
}