package net.zaharenko424.a_changed.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import net.zaharenko424.a_changed.AChanged;
import net.zaharenko424.a_changed.client.cmrs.animation.Animations;
import net.zaharenko424.a_changed.client.cmrs.animation.KeyframeAnimator;
import net.zaharenko424.a_changed.client.cmrs.geom.CubeUV;
import net.zaharenko424.a_changed.client.cmrs.geom.GroupBuilder;
import net.zaharenko424.a_changed.client.cmrs.geom.GroupDefinition;
import net.zaharenko424.a_changed.client.cmrs.geom.ModelDefinition;
import net.zaharenko424.a_changed.client.cmrs.model.CustomHumanoidModel;
import net.zaharenko424.a_changed.registry.TransfurRegistry;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class LatexSharkFemaleModel <E extends LivingEntity> extends CustomHumanoidModel<E> {

    public static final ModelLayerLocation bodyLayer = new ModelLayerLocation(TransfurRegistry.LATEX_SHARK_F_TF.getId(), "main");
    private static final ResourceLocation TEXTURE = AChanged.textureLoc("entity/latex_shark_female");

    public LatexSharkFemaleModel() {
        super(bodyLayer, TEXTURE);
    }

    AnimationState tail_swim = new AnimationState();
    AnimationState tail_swing = new AnimationState();

    @Override
    public void setupAnim(@NotNull E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
        tail_swim.animateWhen(entity.isSwimming() && entity.isInWaterOrBubble(), (int) ageInTicks);
        tail_swing.animateWhen(entity.isInWaterOrBubble() || entity.walkDist != entity.walkDistO, (int) ageInTicks);
        if(tail_swim.isStarted()) KeyframeAnimator.animate(tail_swim, root(), Animations.TAIL_SHARK_SWIM, ageInTicks);
        if(tail_swing.isStarted()) KeyframeAnimator.animate(tail_swing, root(), Animations.TAIL_SHARK_SWING, ageInTicks);
    }

    public static @NotNull ModelDefinition bodyLayer() {
        ModelDefinition.Builder modelBuilder = new ModelDefinition.Builder();
        GroupDefinition groupDefinition = modelBuilder.getRoot();

        GroupDefinition root = groupDefinition.addOrReplaceChild("root", GroupBuilder.create());
        GroupDefinition head = root.addOrReplaceChild("head", GroupBuilder.create()
                .addBox(-4f, 0f, -4f, 8, 8, 8, new CubeUV().south(24, 8, 16, 0).north(8, 20, 0, 12).west(24, 16, 16, 8).up(24, 24, 16, 16).down(8, 20, 0, 28).east(16, 20, 8, 12))
                .addBox(-1.6f, 1f, -6.5f, 3.2f, 2, 2.5f, new CubeUV().south(57, 47, 54, 45).north(46, 22, 43, 20).west(29.5f, 57, 27, 55).up(19, 55.5f, 16, 53).down(22, 53, 19, 55.5f).east(57.5f, 26, 55, 24))
                .addBox(-1.1f, 0f, -5.8f, 2.2f, 1, 1.8f, new CubeUV().south(55, 22, 53, 21).north(54, 38, 52, 37).west(60, 48, 58, 47).up(42, 32, 40, 30).down(38, 40, 36, 42).east(55, 7, 53, 6)), PartPose.offset(0, 24, 0));
        GroupDefinition fins = head.addOrReplaceChild("fins", GroupBuilder.create());
        fins.addOrReplaceChild("side_top", GroupBuilder.create()
                .addMesh(new float[]{2.75f, 13, 6, 2.65f, 7.7f, -3.4f, 4.2464f, 6.9121f, 4, 4.0757f, 6.9121f, -3.5f, 2.45f, 13, 6, 2.55f, 7.7f, -3.4f, 1.1536f, 6.5121f, 4, 1.2536f, 6.6121f, -3.5f, 2.75f, 12.3f, 2.7f, 2.45f, 12.3f, 2.7f, 2.75f, 11.1f, 0.2f, 2.45f, 11.1f, 0.2f, 2.75f, 9.6f, -1.9f, 2.45f, 9.6f, -1.9f, 1.1536f, 6.5121f, 2, 1.1536f, 6.5121f, 0, 1.1536f, 6.5121f, -2, 4.2464f, 6.9121f, 2, 4.2464f, 6.9121f, 0, 4.2464f, 6.9121f, -2, 4.0879f, 10.695f, 4.9f, 1, 10.695f, 4.9f, 1, 10.345f, 2.35f, 1.225f, 9.795f, 0.1f, 1.425f, 8.475f, -2, 1.875f, 7.15f, -3.35f, 3.425f, 7.25f, -3.35f, 3.775f, 8.475f, -2, 3.9629f, 9.795f, 0.1f, 4.0879f, 10.345f, 2.35f, 4.45f, 8.8407f, 2.175f, 4.45f, 9.0157f, 4.45f, 0.75f, 9.0157f, 4.45f, 0.75f, 8.8407f, 2.175f, 0.8625f, 8.5657f, 0.05f, 1.1125f, 7.5875f, -2, 1.5375f, 6.775f, -3.425f, 4.3875f, 8.5657f, 0.05f, 4.0875f, 7.6875f, -2, 3.6504f, 7.0957f, -3.425f}, new float[]{12, 18, 58, 27, 17, 58.9033f, 26, 17.394f, 60.7172f, 1, 18, 60.4187f, 5, 56.5775f, 15, 25, 56.2049f, 15.7886f, 24, 58.0986f, 16.2299f, 13, 59, 15, 5, 48.2f, 61.427f, 13, 48.3999f, 59.0124f, 12, 48.1001f, 59, 1, 48.1001f, 61.4229f, 4, 17.656f, 50, 21, 16.2061f, 53, 20, 19.2939f, 53, 0, 17.9561f, 50, 0, 25, 57, 8, 25, 60, 9, 26, 60, 4, 26, 57, 8, 39, 57, 10, 39, 60, 11, 40, 60, 9, 40, 57, 10, 46, 57, 12, 46, 60, 13, 47, 60, 11, 47, 57, 9, 31.1299f, 48, 22, 30.3811f, 50.3423f, 21, 32.9507f, 50.4906f, 4, 34.5f, 48, 11, 53.7358f, 16, 23, 53.0788f, 17.6679f, 22, 55.3526f, 18.1636f, 9, 56.5f, 16, 13, 54.2114f, 38.244f, 24, 54.2114f, 39.7692f, 23, 56.5f, 38.7924f, 11, 56.4724f, 37, 0, 32, 51, 20, 30, 52.5555f, 29, 30.1583f, 55.1245f, 8, 32, 54.3709f, 8, 51, 53, 29, 49, 54.1656f, 28, 49.4566f, 56.4398f, 10, 51, 55.7636f, 10, 45.7397f, 54, 28, 43.9553f, 54, 27, 43, 56.2967f, 12, 44.525f, 56.277f, 21, 53.4f, 19, 32, 53.15f, 21, 31, 56.85f, 21, 20, 56.4879f, 19, 22, 55.4021f, 52, 33, 55.0241f, 53.4877f, 32, 57.302f, 53.6218f, 21, 57.9759f, 52, 23, 57.6795f, 2, 34, 57.3747f, 3.2459f, 33, 59.5112f, 3.4445f, 22, 60, 2, 24, 57.5121f, 23, 35, 57.0663f, 23.8285f, 34, 59.3357f, 24.096f, 23, 60, 23, 25, 59.592f, 11, 36, 59.358f, 11.4533f, 35, 61.0306f, 11.7248f, 24, 61.5f, 11, 27, 26, 52, 38, 25, 52.4627f, 39, 25.524f, 53.9784f, 26, 26, 53.8484f, 28, 12, 58, 37, 11, 58.6625f, 38, 11.371f, 60.8819f, 27, 12, 60.4873f, 29, 19, 58, 30, 18, 58.5076f, 37, 18.217f, 60.6402f, 28, 19, 60.3166f, 20, 48, 54, 31, 46, 54.6742f, 30, 46.1328f, 56.9521f, 29, 48, 56.5737f, 17, 55.1524f, 24, 30, 57, 23.6777f, 31, 57, 21.3959f, 2, 55, 22.0059f, 2, 48.2888f, 52, 31, 48.8271f, 49.9074f, 32, 45.1729f, 49.3273f, 6, 45.1729f, 51.9014f, 6, 54.3869f, 52, 32, 56.9625f, 52, 33, 56.3949f, 50, 14, 54.0375f, 50.2408f, 14, 22.0898f, 57.3798f, 33, 24.4566f, 57.5f, 34, 24.1173f, 55.3813f, 15, 22.0434f, 55.3813f, 15, 56.4254f, 33, 34, 58.5f, 33, 35, 57.4473f, 31, 16, 56.3771f, 31.0317f, 16, 4.4741f, 60, 35, 5, 59.0611f, 36, 3.3054f, 59.0611f, 7, 3.2355f, 59.1961f, 18, 58, 49.5f, 37, 58, 47.8397f, 30, 56, 47.4965f, 17, 56.1172f, 49.4397f, 19, 20, 59, 38, 20.7915f, 59, 37, 21.5917f, 57, 18, 20, 57.1538f, 3, 5, 60.6222f, 39, 6, 60.6037f, 38, 6, 59, 19, 5.0308f, 59.2428f}, true), PartPose.rotation(0, 0, 0.7854f));
        fins.addOrReplaceChild("side_top_i0", GroupBuilder.create()
                .addMesh(new float[]{-2.75f, 13, 6, -2.65f, 7.7f, -3.4f, -4.2464f, 6.9121f, 4, -4.0757f, 6.9121f, -3.5f, -2.45f, 13, 6, -2.55f, 7.7f, -3.4f, -1.1536f, 6.5121f, 4, -1.2536f, 6.6121f, -3.5f, -2.75f, 12.3f, 2.7f, -2.45f, 12.3f, 2.7f, -2.75f, 11.1f, 0.2f, -2.45f, 11.1f, 0.2f, -2.75f, 9.6f, -1.9f, -2.45f, 9.6f, -1.9f, -1.1536f, 6.5121f, 2, -1.1536f, 6.5121f, 0, -1.1536f, 6.5121f, -2, -4.2464f, 6.9121f, 2, -4.2464f, 6.9121f, 0, -4.2464f, 6.9121f, -2, -4.0879f, 10.695f, 4.9f, -1, 10.695f, 4.9f, -1, 10.345f, 2.35f, -1.225f, 9.795f, 0.1f, -1.425f, 8.475f, -2, -1.875f, 7.15f, -3.35f, -3.425f, 7.25f, -3.35f, -3.775f, 8.475f, -2, -3.9629f, 9.795f, 0.1f, -4.0879f, 10.345f, 2.35f, -4.45f, 8.8407f, 2.175f, -4.45f, 9.0157f, 4.45f, -0.75f, 9.0157f, 4.45f, -0.75f, 8.8407f, 2.175f, -0.8625f, 8.5657f, 0.05f, -1.1125f, 7.5875f, -2, -1.5375f, 6.775f, -3.425f, -4.3875f, 8.5657f, 0.05f, -4.0875f, 7.6875f, -2, -3.6504f, 7.0957f, -3.425f}, new float[]{26, 56.2828f, 36.8348f, 27, 58.0967f, 37.2289f, 12, 59, 36, 1, 56.5813f, 36, 24, 14, 58.9014f, 25, 14.4412f, 60.7951f, 5, 15, 60.4225f, 13, 15, 58, 12, 59.4635f, 52, 13, 59.4511f, 51, 5, 57.0365f, 51.1998f, 1, 57.0406f, 52, 20, 50.2061f, 19, 21, 53.2939f, 19, 4, 51.8439f, 16, 0, 51.5439f, 16, 9, 47, 60, 8, 48, 60, 0, 48, 57, 4, 47, 57, 11, 49, 60, 10, 50, 60, 8, 50, 57, 9, 49, 57, 13, 50, 60, 12, 51, 60, 10, 51, 57, 11, 50, 57, 21, 32, 52.5493f, 22, 32.1483f, 55.1189f, 9, 34, 54.3702f, 4, 34, 51, 22, 8, 55.1474f, 23, 8.4957f, 57.4212f, 11, 10, 56.7642f, 9, 10, 54, 23, 4.9769f, 55, 24, 4, 57, 13, 5.5252f, 57, 11, 6.7692f, 55.0276f, 29, 48.3755f, 32.2693f, 20, 50.9445f, 32.4276f, 0, 52.5f, 30, 8, 49.1291f, 30, 28, 53.0602f, 43.6353f, 29, 55.3344f, 44.0919f, 8, 56.5f, 42, 10, 53.7364f, 42, 27, 55, 8.7397f, 28, 57, 7.7844f, 10, 57, 6, 12, 55.0198f, 7.2147f, 31, 53.15f, 31, 32, 56.85f, 31, 21, 56.6f, 29, 20, 53.5121f, 29, 32, 54, 47.674f, 33, 54.1341f, 49.9518f, 22, 56, 49.5738f, 21, 56, 47, 33, 19, 58.4888f, 34, 19.1986f, 60.6253f, 23, 20, 60.3205f, 22, 20, 58, 34, 52, 57.6643f, 35, 52.2675f, 59.9337f, 24, 53, 59.4879f, 23, 53, 57, 35, 44, 58.4694f, 36, 44.2715f, 60.142f, 25, 45, 59.908f, 24, 45, 58, 39, 59.0216f, 18.1857f, 38, 60.5373f, 18.7097f, 27, 61, 18, 26, 59.1516f, 18, 38, 57.1181f, 19.7492f, 37, 59.3375f, 20.1203f, 28, 60, 19, 27, 57.5127f, 19, 37, 55.3598f, 57.255f, 30, 57.4924f, 57.472f, 29, 58, 56, 28, 55.6834f, 56, 30, 55.0239f, 41.5101f, 31, 57.3018f, 41.6429f, 20, 57.9761f, 40, 29, 55.4024f, 40, 31, 14.802f, 55.4273f, 30, 12.5203f, 55.4273f, 17, 12.198f, 57.3476f, 2, 14.192f, 57.5f, 32, 52.7864f, 7, 31, 52.3221f, 3.3292f, 2, 50.2136f, 3.8012f, 6, 50.2136f, 6.9186f, 33, 56.355f, 26.6427f, 32, 54.145f, 26.0751f, 6, 54.145f, 28.6506f, 14, 56.1142f, 29, 34, 58.289f, 17.2901f, 33, 56.1629f, 17, 14, 56.338f, 19, 15, 58.3371f, 19, 35, 32.0282f, 57.4298f, 34, 30, 56.3771f, 15, 30, 58.4518f, 16, 31.9965f, 58.5f, 36, 54, 45.9301f, 35, 54, 44.2355f, 16, 53, 44.7614f, 7, 53.8039f, 46, 30, 37.2518f, 58.5f, 37, 36.9086f, 56.384f, 18, 35.2482f, 56.384f, 17, 35.3085f, 58.3828f, 37, 58.5f, 43, 38, 56.3968f, 43.8001f, 19, 56.3968f, 45, 18, 58.3462f, 45, 38, 39, 59, 39, 37.3963f, 59, 3, 37.3778f, 59.469f, 19, 38.7572f, 59.4382f}, true), PartPose.rotation(0, 0, -0.7854f));
        head.addOrReplaceChild("armor_head", GroupBuilder.create()
                .addBox(-4f, 0f, -4f, 8, 8, 8, new Vector3f(0.6f), new CubeUV().south(32, 16, 24, 8).north(16, 16, 8, 8).west(24, 16, 16, 8).up(16, 8, 8, 0).down(24, 0, 16, 8).east(8, 16, 0, 8)).armor());
        GroupDefinition body = root.addOrReplaceChild("body", GroupBuilder.create()
                .addBox(-4f, 0f, -2f, 8, 12, 4, new CubeUV().south(16, 12, 8, 0).north(8, 12, 0, 0).west(16, 32, 12, 20).up(36, 16, 28, 12).down(36, 16, 28, 20).east(12, 32, 8, 20)), PartPose.offset(0, 12, 0));
        GroupDefinition tail = body.addOrReplaceChild("tail", GroupBuilder.create()
                .addBox(-2f, -1.887f, -2.1383f, 4, 4, 7, new CubeUV().south(12, 49, 8, 45).north(8, 49, 4, 45).west(42, 36, 36, 32).up(42, 42, 38, 36).down(4, 40, 0, 46).east(38, 40, 32, 36)), PartPose.offsetAndRotation(0, 1, 2, 0.7854f, 0, 0));
        GroupDefinition tail_0 = tail.addOrReplaceChild("tail0", GroupBuilder.create()
                .addBox(-1.5f, -1.387f, -0.6383f, 3, 3, 6, new CubeUV().south(23, 53, 20, 50).north(53, 22, 50, 19).west(46, 16, 40, 13).up(15, 46, 12, 40).down(43, 16, 40, 22).east(46, 13, 40, 10)), PartPose.offsetAndRotation(0, 0, 4.5f, -0.1309f, 0, 0));
        GroupDefinition tail_1 = tail_0.addOrReplaceChild("tail1", GroupBuilder.create()
                .addBox(-1f, -0.887f, -0.6383f, 2, 2, 5, new CubeUV().south(55, 58, 53, 56).north(46, 10, 44, 8).west(54, 40, 49, 38).up(10, 54, 8, 49).down(12, 49, 10, 54).east(54, 11, 49, 9)), PartPose.offsetAndRotation(0, 0, 5, -0.096f, 0, 0));
        GroupDefinition tail_2 = tail_1.addOrReplaceChild("tail2", GroupBuilder.create(), PartPose.offsetAndRotation(0, 0, 4, -0.1309f, 0, 0));
        tail_2.addOrReplaceChild("tail_fin", GroupBuilder.create()
                .addMesh(new float[]{-8.7698f, 1.0155f, 22.6844f, -2.3695f, 1.0155f, 14.269f, -0.0029f, 2.7329f, 20.0989f, -0.0029f, 2.084f, 14.1645f, -8.7698f, 0.9109f, 22.6844f, -2.3695f, 0.9109f, 14.269f, -0.0029f, -0.8065f, 20.0989f, -0.0029f, -0.1576f, 14.1645f, -8.0866f, 1.0155f, 20.1017f, -8.0866f, 0.9109f, 20.1017f, -6.8216f, 1.0155f, 17.6334f, -6.8216f, 0.9109f, 17.6334f, -4.7493f, 1.0155f, 15.7955f, -4.7493f, 0.9109f, 15.7955f, -0.0029f, -0.8785f, 18.8593f, -0.0029f, -0.8497f, 17.0864f, -0.0029f, -0.6769f, 15.4441f, -0.0029f, 2.8049f, 18.8593f, -0.0029f, 2.7761f, 17.0864f, -0.0029f, 2.6033f, 15.4441f, -0.7706f, 1.0155f, 13.4584f, -0.7706f, 0.9109f, 13.4584f, -0.0029f, 1.8009f, 13.3539f, -0.0029f, 0.1255f, 13.3539f, 8.7641f, 0.9109f, 22.6844f, 2.3637f, 0.9109f, 14.269f, 8.7641f, 1.0155f, 22.6844f, 2.3637f, 1.0155f, 14.269f, 8.0809f, 0.9109f, 20.1017f, 8.0809f, 1.0155f, 20.1017f, 6.8158f, 0.9109f, 17.6334f, 6.8158f, 1.0155f, 17.6334f, 4.7435f, 0.9109f, 15.7955f, 4.7435f, 1.0155f, 15.7955f, 0.7649f, 0.9109f, 13.4584f, 0.7649f, 1.0155f, 13.4584f, -8.7698f, 1.0155f, 24.5564f, -8.7698f, 0.9109f, 24.5564f, -0.0029f, 2.0849f, 20.8189f, -0.0029f, -0.1585f, 20.8189f, 8.7641f, 0.9109f, 24.5564f, 8.7641f, 1.0155f, 24.5564f, -4.0264f, 1.2622f, 23.1916f, -4.0264f, 0.6642f, 23.1916f, -4.6024f, -0.1998f, 21.6076f, -4.1168f, -0.1998f, 19.4805f, -3.4122f, -0.1134f, 17.3599f, -2.3041f, 0.045f, 15.6198f, -1.1142f, 0.3766f, 14.2167f, -0.4868f, 0.4182f, 13.4062f, -0.4868f, 1.5082f, 13.4062f, -1.1142f, 1.5498f, 14.2167f, -2.3041f, 1.8814f, 15.6198f, -3.4122f, 2.0398f, 17.3599f, -4.1168f, 2.1262f, 19.4805f, -4.6024f, 2.1262f, 21.6076f, 4.0207f, 1.2622f, 23.1916f, 4.0207f, 0.6642f, 23.1916f, 4.5967f, -0.1998f, 21.6077f, 4.1111f, -0.1998f, 19.4805f, 3.4065f, -0.1134f, 17.3599f, 2.2984f, 0.045f, 15.6198f, 1.1084f, 0.3767f, 14.2167f, 0.481f, 0.4182f, 13.4062f, 0.481f, 1.5082f, 13.4062f, 1.1084f, 1.5497f, 14.2167f, 2.2984f, 1.8814f, 15.6198f, 3.4065f, 2.0398f, 17.3599f, 4.1111f, 2.1262f, 19.4805f, 4.5967f, 2.1262f, 21.6077f, -1.344f, 1.7146f, 22.1378f, -1.344f, 0.2118f, 22.1378f, -2.6852f, 1.4884f, 22.8807f, -2.6852f, 0.438f, 22.8807f, -1.344f, 2.3986f, 21.4178f, -2.6852f, 2.1004f, 22.0167f, -1.344f, -0.4362f, 21.4178f, -2.6852f, -0.138f, 22.0167f, 1.3383f, 0.2118f, 22.1378f, 1.3383f, 1.7146f, 22.1378f, 2.6794f, 0.438f, 22.8807f, 2.6794f, 1.4884f, 22.8807f, 1.3383f, 2.3626f, 21.4178f, 2.6794f, 2.0644f, 22.0167f, 2.6794f, -0.174f, 22.0167f, 1.3383f, -0.4722f, 21.4178f}, new float[]{5, 43, 58, 13, 46, 58, 12, 46, 57, 1, 43, 57, 0, 53, 29, 8, 50, 29, 9, 50, 30, 4, 53, 30, 8, 3, 52, 10, 0, 52, 11, 0, 53, 9, 3, 53, 10, 60, 14, 12, 57, 14, 13, 57, 15, 11, 60, 15, 5, 60, 53, 1, 60, 52, 20, 58, 52, 21, 58, 53, 13, 5.9268f, 51.1695f, 47, 3.3269f, 51.1695f, 46, 4.434f, 52.9176f, 11, 8, 53, 5, 54.1745f, 54.0427f, 48, 53.089f, 54.8708f, 47, 54.8493f, 55.5f, 13, 57, 54.0427f, 21, 20.3469f, 59.3052f, 49, 20.0468f, 59.7909f, 48, 21.0511f, 60, 5, 22, 59.3052f, 20, 32, 59.4927f, 50, 31, 59, 49, 31, 60.09f, 21, 32, 59.5973f, 1, 59.2859f, 17.1063f, 51, 60, 15.9426f, 50, 59.5952f, 15, 20, 59.2859f, 15.345f, 12, 51.0432f, 57.9129f, 52, 52.5f, 55.7594f, 51, 51.8685f, 54, 1, 51.0432f, 55.0861f, 10, 14, 54.7164f, 53, 14, 51.146f, 52, 12.0567f, 50, 12, 12, 52.5968f, 8, 50, 8.1328f, 54, 50, 3.9639f, 53, 48.2667f, 3, 10, 48, 6.5604f, 0, 30, 51.4159f, 55, 30, 46.9705f, 54, 28.3982f, 46, 8, 28, 50.1498f, 9, 41.83f, 22.0875f, 45, 40.3408f, 26, 44, 42.5227f, 26, 4, 44.5f, 22, 11, 51.9278f, 12, 46, 48.3675f, 12.2667f, 45, 49.3313f, 14, 9, 53.5f, 14, 27, 60, 42, 33, 57, 42, 32, 57, 43, 25, 60, 43, 24, 48, 36, 28, 51, 36, 29, 51, 35, 26, 48, 35, 28, 51, 27, 30, 54, 27, 31, 54, 26, 29, 51, 26, 30, 56, 56, 32, 59, 56, 33, 59, 55, 31, 56, 55, 27, 53, 58, 25, 53, 59, 34, 55, 59, 35, 55, 58, 18, 57.4987f, 9, 67, 54, 9, 66, 55.1825f, 10.6978f, 19, 57.5907f, 10.6352f, 19, 6.4182f, 57, 66, 4, 57, 65, 5.1353f, 58.4851f, 3, 6.3561f, 58.3723f, 3, 60.2342f, 30, 65, 59, 30, 64, 59.5814f, 30.8453f, 22, 60.146f, 30.8518f, 22, 3, 59.3246f, 64, 2, 59.6173f, 63, 2, 60.7073f, 23, 3, 61, 23, 60, 29.4286f, 63, 60, 28.8607f, 62, 59.0044f, 28.2729f, 7, 59, 29.5f, 7, 58.356f, 8.4655f, 62, 58.4801f, 7.2376f, 61, 57, 6.0957f, 16, 57, 8.5f, 16, 30, 55, 61, 30, 52.5817f, 60, 28, 51.4481f, 15, 28.1084f, 54.9323f, 15, 24.7451f, 54.5f, 60, 25.0067f, 51.0112f, 59, 23, 50.0242f, 14, 23, 54.2362f, 14, 42.2336f, 54.5f, 59, 42.9374f, 50.3436f, 58, 41.0626f, 49.2276f, 6, 41.0626f, 54.1047f, 2, 53.9932f, 40, 69, 49.1146f, 40, 68, 50.2302f, 41.875f, 17, 54.3854f, 41.1728f, 17, 53.2156f, 50, 68, 49, 50, 67, 49.9861f, 52.0071f, 18, 53.4722f, 51.7483f, 37, 57.0527f, 50.5071f, 43, 61.9701f, 51, 42, 62, 50.4027f, 36, 57.0579f, 50.4027f, 36, 60, 49, 0, 58, 49, 4, 58, 50, 37, 60, 50, 40, 58, 49, 24, 60, 49, 26, 60, 48, 41, 58, 48, 36, 49, 49.7053f, 42, 54, 49.894f, 55, 54, 48, 0, 49.5772f, 48.1397f, 4, 14, 50.5546f, 44, 14, 55, 43, 15.893f, 54.9405f, 37, 15.552f, 50.1012f, 6, 46.5069f, 38, 44, 51.3854f, 38, 45, 50.2697f, 36, 14, 46.1146f, 36.7023f, 14, 50.2844f, 24, 45, 54.5f, 24, 46, 53.5139f, 22, 15, 50.0278f, 22.2588f, 15, 53.5014f, 35, 46, 57, 35, 47, 55.8175f, 33, 16, 53.4094f, 33.0626f, 16, 57.0818f, 55, 47, 59.5f, 55, 48, 58.3647f, 54, 7, 57.1439f, 54.1128f, 7, 59.2658f, 28, 48, 60.5f, 28, 49, 59.9187f, 27.0066f, 23, 59.354f, 27, 23, 51, 59.6754f, 49, 52, 59.3827f, 50, 52, 58.2927f, 22, 51, 58, 22, 34.1547f, 59.0713f, 50, 34.1547f, 59.6393f, 51, 34.9956f, 60.2271f, 3, 35, 59, 3, 29.1241f, 58.0345f, 51, 29, 59.2624f, 52, 30, 60.4043f, 19, 30, 58, 19, 25.2692f, 54, 52, 25.2692f, 56.4182f, 53, 27, 57.5519f, 18, 26.8918f, 54.0677f, 18, 26.2616f, 50, 53, 26, 53.4888f, 54, 28, 54.4758f, 17, 28, 50.2637f, 17, 46.7039f, 30, 54, 46, 34.1563f, 55, 48, 35.2724f, 2, 48, 30.3953f, 41, 37.9472f, 55.4928f, 56, 33.0299f, 55, 57, 33, 56, 40, 37.942f, 56, 40, 54.9384f, 7.2946f, 57, 50, 7.1061f, 58, 50, 9, 24, 54.3613f, 8.8602f, 24, 0, 46.0842f, 58, 0, 50.5295f, 59, 1.9541f, 51.5f, 28, 2.3524f, 47.3503f, 28, 35, 45.3672f, 59, 35, 49.5361f, 60, 37.0179f, 50.5f, 30, 37.2846f, 46.9398f, 30, 34, 50.2836f, 60, 34, 53.854f, 61, 35.7227f, 55, 32, 35.7795f, 52.4032f, 32, 41, 50.087f, 61, 40, 52.2405f, 62, 40.6315f, 54, 25, 41, 52.9138f, 25, 46, 58.3938f, 62, 45, 59.5574f, 63, 45.4048f, 60.5f, 34, 46, 60.155f, 34, 32, 60.0073f, 63, 33, 60.5f, 64, 33, 59.41f, 35, 32, 59.9027f, 35, 59.653f, 57, 64, 59.9531f, 56.2089f, 65, 58.9487f, 56, 27, 58, 57, 27, 51.8254f, 12, 65, 52.911f, 11.6292f, 66, 51.1507f, 11, 33, 49, 12, 33, 51.0732f, 29, 66, 53.6731f, 29, 67, 52.566f, 27.0822f, 31, 49, 27, 31, 45.5722f, 29.2846f, 67, 49.1326f, 29.0178f, 68, 48.1686f, 27, 29, 44, 27, 29, 42.67f, 29.8938f, 68, 44.1591f, 26, 69, 41.9773f, 26, 26, 40, 29.9813f, 26, 45, 53.4452f, 69, 45, 49, 56, 43, 49.0596f, 41, 43.341f, 53.8988f, 43, 33, 60.2738f, 73, 34, 60.5f, 72, 34, 59.4496f, 42, 33, 59.6758f, 38, 4, 56.2566f, 70, 2, 56.6268f, 71, 2, 58.1297f, 39, 4, 58.5f, 70, 59, 33.4971f, 72, 57, 33.7234f, 73, 57, 34.7738f, 71, 59, 35, 2, 59.9392f, 13, 55, 55.0608f, 13, 75, 56.7385f, 14.0141f, 74, 58.2254f, 13.8406f, 70, 60.5762f, 1.8981f, 74, 61, 1, 75, 59.5013f, 1, 72, 59.0342f, 1.9383f, 72, 33.6156f, 58.1488f, 75, 35, 57.9605f, 55, 35, 56, 42, 33, 56.9051f, 38, 60.3896f, 53.7521f, 2, 61, 53, 74, 59.0895f, 53, 70, 58.4728f, 53.778f, 73, 59, 12.7141f, 43, 59, 11.3188f, 44, 57, 11.2582f, 77, 57.898f, 13, 39, 23, 60.3795f, 71, 23, 58.4624f, 76, 22, 59.0829f, 6, 22, 61, 71, 59.5f, 10.0416f, 73, 58.1403f, 9.2978f, 77, 58.1403f, 10.3362f, 76, 59.477f, 11, 76, 16.0804f, 58.2879f, 77, 16.2604f, 56.7999f, 44, 15.2396f, 55.1253f, 6, 15.2396f, 60, 56, 60, 32.2262f, 81, 59, 32, 80, 59, 33.0504f, 57, 60, 32.8242f, 39, 41, 57.5f, 78, 43, 57.1297f, 79, 43, 55.6269f, 38, 41, 55.2566f, 78, 57, 39.5028f, 80, 59, 39.2766f, 81, 59, 38.2262f, 79, 57, 38, 82, 24, 59.9171f, 2, 24, 58, 38, 23, 58.6205f, 79, 23, 60.5375f, 69, 59.5f, 4, 83, 57.5386f, 4, 81, 57.3403f, 5.0193f, 56, 58.5795f, 5.6517f, 69, 49, 58.8786f, 2, 49, 54, 82, 48.1696f, 55.7184f, 83, 48, 57.2052f, 83, 23, 54.4988f, 82, 23, 53, 79, 22.0446f, 53.4166f, 81, 22, 54.9586f, 58, 2, 59, 57, 2, 57.106f, 80, 0.324f, 57.0613f, 84, 0, 58.0631f, 78, 55.4239f, 58.0402f, 85, 55, 59, 84, 56.4988f, 59, 80, 56.9659f, 58, 85, 56.7746f, 35.1659f, 6, 55.0607f, 36, 58, 59.9393f, 36, 84, 58.2614f, 35, 78, 59.5375f, 58.2306f, 39, 57.6205f, 58.2306f, 6, 57, 58.9744f, 85, 58.9101f, 59}, true), PartPose.offset(0, -0.887f, -13.0383f));
        GroupDefinition armor_body = body.addOrReplaceChild("armor_body", GroupBuilder.create()
                .addBox(-4f, 0f, -2f, 8, 12, 4, new Vector3f(0.6f), new CubeUV().south(40, 32, 32, 20).north(28, 32, 20, 20).west(20, 32, 16, 20).up(40, 11, 32, 7).down(40, 34, 32, 38).east(32, 32, 28, 20)).armor());
        armor_body.addOrReplaceChild("cube", GroupBuilder.create()
                .addBox(-1.7f, -1.8f, -1.5f, 3.5f, 3.5f, 3, new Vector3f(0.6f), new CubeUV().south(22.5f, 51.5f, 19, 48).north(12, 20, 8, 16).west(37, 28, 34, 24.5f).up(52.5f, 3, 49, 0).down(12, 16, 8, 20).east(38, 28, 35, 24.5f))
                .addBox(2.2f, -1.8f, -1.5f, 3.5f, 3.5f, 3, new Vector3f(0.6f), new CubeUV().south(50.5f, 31.5f, 47, 28).north(12, 20, 8, 16).west(37, 28, 34, 24.5f).up(38.5f, 51, 35, 48).down(12, 16, 8, 20).east(37, 28, 34, 24.5f)), PartPose.offsetAndRotation(-2, 8.3f, -2, 0.48f, 0, 0));
        body.addOrReplaceChild("cube_i0", GroupBuilder.create()
                .addBox(-1.7f, -1.8f, -1.5f, 3.5f, 3.5f, 3, new CubeUV().south(19.5f, 49.5f, 16, 46).north(15.5f, 49.5f, 12, 46).west(49, 41.5f, 46, 38).up(44.5f, 49, 41, 46).down(48.5f, 46, 45, 49).east(41, 49.5f, 38, 46))
                .addBox(2.2f, -1.8f, -1.5f, 3.5f, 3.5f, 3, new CubeUV().south(27.5f, 49.5f, 24, 46).north(23.5f, 49.5f, 20, 46).west(50, 23.5f, 47, 20).up(50.5f, 27, 47, 24).down(51.5f, 0, 48, 3).east(50, 19.5f, 47, 16)), PartPose.offsetAndRotation(-2, 8.3f, -2, 0.48f, 0, 0));
        body.addOrReplaceChild("back_fin", GroupBuilder.create()
                .addMesh(new float[]{0.04f, 6.8f, 4, 0.04f, 3.04f, -3.12f, 1.52f, 2, 2.4f, 1.44f, 2, -3.2f, -0.04f, 6.8f, 4, -0.04f, 3.04f, -3.12f, -1.52f, 2, 2.4f, -1.44f, 2, -3.2f, 0.04f, 6.24f, 1.56f, -0.04f, 6.24f, 1.56f, 0.04f, 5.53f, -0.34f, -0.04f, 5.53f, -0.34f, 0.04f, 4.24f, -2, -0.04f, 4.24f, -2, -1.52f, 2, 0.9f, -1.52f, 2, -0.6f, -1.52f, 2, -2, 1.52f, 2, 0.9f, 1.52f, 2, -0.6f, 1.52f, 2, -2, 0.04f, 2.16f, -3.52f, -0.04f, 2.16f, -3.52f, 0.8f, 2, -3.6f, -0.8f, 2, -3.6f}, new float[]{5, 41, 24, 13, 41, 22, 12, 40, 22, 1, 40, 24, 0, 3, 46, 8, 3, 49, 9, 4, 49, 4, 4, 46, 8, 15, 40, 10, 15, 42, 11, 16, 42, 9, 16, 40, 10, 31, 42, 12, 31, 44, 13, 32, 44, 11, 32, 42, 5, 41, 26, 1, 40, 26, 20, 40, 27, 21, 41, 27, 4, 46.841f, 21.2058f, 6, 44, 25.6465f, 2, 47, 26.5f, 0, 47, 21.2283f, 13, 10.17f, 55.0763f, 16, 10.17f, 58, 15, 11.57f, 58, 11, 11.83f, 54, 5, 57.1487f, 28.7662f, 7, 57.1487f, 30.512f, 16, 58.3513f, 30.5212f, 13, 58.2721f, 28, 21, 59, 7.2663f, 23, 59.0593f, 8.0448f, 7, 60, 8.5887f, 5, 60, 7, 20, 6.96f, 59, 22, 6.2f, 60, 23, 7.8f, 60, 21, 7.04f, 59, 1, 28.7458f, 59, 3, 27, 59, 22, 27.4949f, 59.5698f, 20, 28.2033f, 59.752f, 12, 59.6848f, 21, 19, 57, 21, 3, 57.0441f, 22.2018f, 1, 58.6836f, 22.155f, 10, 36, 51, 18, 36.26f, 55, 19, 38, 55, 12, 38, 52.1896f, 8, 38.1088f, 50, 17, 38.1088f, 54.5391f, 18, 39.5929f, 54.7572f, 10, 39.8912f, 50.9394f, 0, 46.1688f, 8, 2, 46.1688f, 13.2716f, 17, 47.598f, 13.7268f, 8, 48.3312f, 9.2504f, 6, 46.7283f, 16, 4, 52, 16, 9, 50.7495f, 14, 14, 46.273f, 14.7437f, 14, 49.461f, 48, 9, 54, 48, 11, 53.0606f, 46, 15, 49.2428f, 46.3262f}, true), PartPose.offsetAndRotation(0, 7.2f, 0, 1.5708f, 0, 0));
        GroupDefinition right_arm = root.addOrReplaceChild("right_arm", GroupBuilder.create(), PartPose.offset(4, 22, 0));
        right_arm.addOrReplaceChild("armor_right_arm", GroupBuilder.create()
                .addBox(0f, -10f, -2f, 4, 12, 4, new Vector3f(0.6f), new CubeUV().south(56, 32, 52, 20).north(48, 32, 44, 20).west(52, 32, 48, 20).up(48, 20, 44, 16).east(44, 32, 40, 20)).armor());
        right_arm.addOrReplaceChild("arm_fin", GroupBuilder.create()
                .addMesh(new float[]{6.0252f, 8.31f, 4.464f, 6.0252f, 3.9044f, 2.4552f, 6.9576f, 7.202f, 1.8f, 6.9072f, 3.854f, 1.8f, 5.9748f, 8.31f, 4.464f, 5.9748f, 3.9044f, 2.4552f, 5.0424f, 7.202f, 1.8f, 5.0928f, 3.854f, 1.8f, 6.0252f, 6.7728f, 4.3012f, 5.9748f, 6.7728f, 4.3012f, 6.0252f, 5.6658f, 3.9439f, 5.9748f, 5.6658f, 3.9439f, 6.0252f, 4.71f, 3.3112f, 5.9748f, 4.71f, 3.3112f, 5.0424f, 6.257f, 1.8f, 5.0424f, 5.402f, 1.8f, 5.0424f, 4.61f, 1.8f, 6.9576f, 6.257f, 1.8f, 6.9576f, 5.402f, 1.8f, 6.9576f, 4.61f, 1.8f, 6.0252f, 3.6524f, 1.9008f, 5.9748f, 3.6524f, 1.9008f, 6.504f, 3.602f, 1.8f, 5.496f, 3.602f, 1.8f}, new float[]{12, 35, 49, 13, 34, 49, 5, 34, 50, 1, 35, 50, 9, 19, 52, 8, 20, 52, 0, 20, 50, 4, 19, 50, 11, 46, 36, 10, 47, 36, 8, 47, 35, 9, 46, 35, 13, 16, 51, 12, 17, 51, 10, 17, 50, 11, 16, 50, 20, 49, 9, 1, 49, 8, 5, 48, 8, 21, 48, 9, 2, 39, 56.4588f, 6, 41, 57.0477f, 4, 41, 54.0155f, 0, 40.7744f, 54, 15, 58.0897f, 31, 16, 58, 31.7869f, 13, 59.7756f, 31.8887f, 11, 60.4189f, 31, 16, 59, 22.2339f, 7, 59, 22.9916f, 5, 60.0998f, 23, 13, 60.6528f, 22.1962f, 7, 59, 44.133f, 23, 59.3426f, 44.4627f, 21, 59.8331f, 44.5f, 5, 60.0009f, 44.133f, 23, 35.246f, 60, 22, 36.254f, 60, 20, 35.7752f, 59, 21, 35.7248f, 59, 22, 30.1147f, 60.1882f, 3, 31, 60.5f, 1, 31, 59.4001f, 20, 30, 59.7419f, 3, 59.2045f, 7, 19, 59.9622f, 7, 12, 60, 5, 1, 59.1961f, 5.7557f, 19, 24.0977f, 60.5f, 18, 25, 60.4112f, 10, 25, 58.0585f, 12, 24, 58.7422f, 18, 12.1839f, 61, 17, 13, 60.8378f, 8, 13, 58.119f, 10, 12, 58.6578f, 17, 7.4951f, 58.5f, 2, 8, 58.1547f, 0, 8, 55.1225f, 8, 7, 55.8273f, 9, 58.6727f, 1.5f, 4, 59.3775f, 0.1242f, 6, 56.3453f, 0.1242f, 14, 56, 1.0032f, 11, 59.3422f, 46.5f, 9, 59.8809f, 45.469f, 14, 57.1622f, 45.469f, 15, 57, 46.3023f}, true)
                .addBox(4f, -0.096f, -2f, 3.6f, 12, 4, new CubeUV().south(20, 36, 16, 24).north(28, 12, 24, 0).west(24, 36, 20, 24).up(36, 12, 32, 8).down(36, 40, 32, 44).east(28, 24, 24, 12)), PartPose.offset(-4, -9.904f, 0));
        GroupDefinition left_arm = root.addOrReplaceChild("left_arm", GroupBuilder.create(), PartPose.offset(-4, 22, 0));
        left_arm.addOrReplaceChild("armor_left_arm", GroupBuilder.create()
                .addBox(-4f, -10f, -2f, 4, 12, 4, new Vector3f(0.6f), new CubeUV().south(48, 32, 44, 20).north(56, 32, 52, 20).west(40, 32, 44, 20).up(44, 20, 48, 16).east(52, 32, 48, 20)).armor());
        left_arm.addOrReplaceChild("arm_fin_i0", GroupBuilder.create()
                .addMesh(new float[]{-6.0252f, 8.31f, 4.464f, -6.0252f, 3.9044f, 2.4552f, -6.9576f, 7.202f, 1.8f, -6.9072f, 3.854f, 1.8f, -5.9748f, 8.31f, 4.464f, -5.9748f, 3.9044f, 2.4552f, -5.0424f, 7.202f, 1.8f, -5.0928f, 3.854f, 1.8f, -6.0252f, 6.7728f, 4.3012f, -5.9748f, 6.7728f, 4.3012f, -6.0252f, 5.6658f, 3.9439f, -5.9748f, 5.6658f, 3.9439f, -6.0252f, 4.71f, 3.3112f, -5.9748f, 4.71f, 3.3112f, -5.0424f, 6.257f, 1.8f, -5.0424f, 5.402f, 1.8f, -5.0424f, 4.61f, 1.8f, -6.9576f, 6.257f, 1.8f, -6.9576f, 5.402f, 1.8f, -6.9576f, 4.61f, 1.8f, -6.0252f, 3.6524f, 1.9008f, -5.9748f, 3.6524f, 1.9008f, -6.504f, 3.602f, 1.8f, -5.496f, 3.602f, 1.8f}, new float[]{5, 3, 47, 13, 3, 46, 12, 2, 46, 1, 2, 47, 0, 30, 46, 8, 30, 48, 9, 31, 48, 4, 31, 46, 8, 44, 29, 10, 44, 30, 11, 45, 30, 9, 45, 29, 10, 46, 20, 12, 46, 21, 13, 47, 21, 11, 47, 20, 5, 36, 44, 1, 35, 44, 20, 35, 45, 21, 36, 45, 4, 3.2584f, 55, 6, 0.2261f, 55, 2, 0.8151f, 56.8224f, 0, 3.2739f, 55.048f, 13, 26, 58.6433f, 16, 26.1018f, 60.4189f, 15, 27, 60.3292f, 11, 27, 58, 5, 16.192f, 59.8944f, 7, 16.192f, 60.9942f, 16, 16.9497f, 61, 13, 17, 59.3476f, 21, 59, 36.6669f, 23, 59.0373f, 37.1574f, 7, 60, 37.5f, 5, 60, 36.4991f, 20, 59.7248f, 34, 22, 59.246f, 35, 23, 60.254f, 35, 21, 59.7752f, 34, 1, 60.0999f, 43.0263f, 3, 59, 43.0263f, 22, 59.3118f, 43.3853f, 20, 59.7581f, 43.5f, 12, 60.7946f, 12.2124f, 19, 59.0161f, 12.2124f, 3, 59, 12.9699f, 1, 60.022f, 13, 10, 60.4415f, 25.1153f, 18, 58.0888f, 25.1153f, 19, 58, 25.9023f, 12, 59.7578f, 26, 8, 10.881f, 58.4766f, 17, 8.1622f, 58.4766f, 18, 8, 59.3161f, 10, 10.3422f, 59.5f, 0, 20.3775f, 56.1253f, 2, 17.3453f, 56.1253f, 17, 17, 57.0049f, 8, 19.6727f, 57.5f, 6, 39, 58.1547f, 4, 39, 55.1225f, 9, 38, 55.8273f, 14, 38.4968f, 58.5f, 14, 14, 60.8378f, 9, 14, 58.1191f, 11, 13, 58.6578f, 15, 13.1977f, 61}, true)
                .addBox(-7.6f, -0.096f, -2f, 3.6f, 12, 4, new CubeUV().south(32, 12, 28, 0).north(28, 36, 24, 24).west(8, 40, 4, 28).up(19, 46, 15, 42).down(23, 42, 19, 46).east(4, 40, 0, 28)), PartPose.offset(4, -9.904f, 0));
        GroupDefinition right_leg = root.addOrReplaceChild("right_leg", GroupBuilder.create(), PartPose.offsetAndRotation(2, 13, 0, 0f, -0.1309f, 0.1309f));
        GroupDefinition right_leg_shin = right_leg.addOrReplaceChild("right_leg_shin", GroupBuilder.create(), PartPose.offset(0, -6, -2));
        GroupDefinition right_leg_ = right_leg_shin.addOrReplaceChild("right_leg_", GroupBuilder.create(), PartPose.offset(0, -1, 3));
        GroupDefinition right_foot = right_leg_.addOrReplaceChild("right_foot", GroupBuilder.create());
        right_foot.addOrReplaceChild("right_foot_i0", GroupBuilder.create()
                .addBox(-1.3f, -6f, -3.5f, 4f, 2, 5, new CubeUV().south(57, 13, 53, 11).north(57, 6, 53, 4).west(8, 51, 3, 49).up(44, 10, 40, 5).down(12, 40, 8, 45).east(53, 46, 48, 44))
                .addMesh(new float[]{-0.915f, -6.025f, 1.025f, -0.725f, -6.025f, -1.325f, 2.315f, -6.025f, 1.025f, 2.125f, -6.025f, -1.325f, 0.7f, -6.025f, -0.3f, -1.2f, -6.025f, 0, 0.7f, -6.025f, 0.9f, 2.6f, -6.025f, 0, 0.7f, -6.025f, -1.8f, -0.5f, -6.025f, -2.5f, -0.7071f, -6.025f, -2, -1, -6.025f, -2.2929f, -1, -6.025f, -2.7071f, -0.7071f, -6.025f, -3, -0.2929f, -6.025f, -3, -0f, -6.025f, -2.7071f, 0, -6.025f, -2.2929f, -0.2929f, -6.025f, -2, 0.7f, -6.025f, -2.5f, 0.4929f, -6.025f, -2, 0.2f, -6.025f, -2.2929f, 0.2f, -6.025f, -2.7071f, 0.4929f, -6.025f, -3, 0.9071f, -6.025f, -3, 1.2f, -6.025f, -2.7071f, 1.2f, -6.025f, -2.2929f, 0.9071f, -6.025f, -2, 1.9f, -6.025f, -2.5f, 1.6929f, -6.025f, -2, 1.4f, -6.025f, -2.2929f, 1.4f, -6.025f, -2.7071f, 1.6929f, -6.025f, -3, 2.1071f, -6.025f, -3, 2.4f, -6.025f, -2.7071f, 2.4f, -6.025f, -2.2929f, 2.1071f, -6.025f, -2}, new float[]{5, 44, 58.3425f, 1, 43.2469f, 59.0184f, 8, 43, 60.5f, 4, 44, 60.2661f, 6, 59.3427f, 41.8128f, 0, 59.2142f, 40.1981f, 5, 58.1573f, 40.0765f, 4, 58.1573f, 42, 7, 1.9618f, 60, 2, 1.8401f, 59.1284f, 6, 0.2254f, 59, 4, 0.0382f, 60, 8, 27.1713f, 58.5f, 3, 28.6529f, 58.2531f, 7, 29.3287f, 57.0184f, 4, 27.4052f, 57.0184f, 9, 55, 14, 12, 55, 13, 11, 54.1585f, 13.1585f, 10, 54, 14, 9, 58, 20, 14, 57, 20, 13, 57.1586f, 20.3827f, 12, 58, 20.5412f, 9, 58.2294f, 59, 16, 58.2294f, 59.5412f, 15, 58.6121f, 59.3827f, 14, 58.7706f, 59, 17, 60.612f, 0.3827f, 16, 60.7705f, 0, 9, 60.2295f, 0, 10, 60.2295f, 0.5412f, 18, 32, 56, 21, 32, 55, 20, 31.1585f, 55.1585f, 19, 31, 56, 18, 59.2294f, 39, 23, 59.7706f, 39, 22, 59.6121f, 38.1585f, 21, 59.2294f, 38, 18, 56, 37, 25, 56, 36, 24, 55.1585f, 36.1585f, 23, 55, 37, 26, 55.6121f, 59.3827f, 25, 55.7706f, 59, 18, 55.2294f, 59, 19, 55.2294f, 59.5412f, 27, 45.2294f, 57, 30, 45.7706f, 57, 29, 45.6121f, 56.1585f, 28, 45.2294f, 56, 27, 54.2294f, 59, 32, 54.2294f, 59.5412f, 31, 54.6121f, 59.3827f, 30, 54.7706f, 59, 27, 0.2294f, 60, 34, 0.2294f, 60.5412f, 33, 0.6121f, 60.3827f, 32, 0.7706f, 60, 35, 56.612f, 59.3827f, 34, 56.7706f, 59, 27, 56.2294f, 59, 28, 56.2294f, 59.5412f}), PartPose.rotation(0, 0, -0.1309f));
        right_foot.addOrReplaceChild("armor_right_foot", GroupBuilder.create()
                .addBox(-1.3f, -6f, -3.5f, 4f, 2, 5, new Vector3f(0.6f), new CubeUV().south(12, 20, 8, 16).north(12, 20, 8, 16).west(12, 20, 8, 16).up(12, 20, 8, 16).down(12, 16, 8, 20).east(12, 20, 8, 16)).armor(), PartPose.rotation(0, 0, -0.1309f));
        right_leg_.addOrReplaceChild("right_leg__i0", GroupBuilder.create()
                .addBox(-2f, -5f, -2f, 4, 6, 4, new CubeUV().south(20, 42, 16, 36).north(40, 14, 36, 8).west(24, 42, 20, 36).up(48, 4, 44, 0).down(48, 4, 44, 8).east(40, 20, 36, 14)), PartPose.rotation(0.1309f, 0, 0));
        right_leg_.addOrReplaceChild("armor_right_leg_", GroupBuilder.create()
                .addBox(-2f, -5f, -2f, 4, 6, 4, new Vector3f(0.6f), new CubeUV().south(4, 32, 0, 26).north(4, 32, 0, 26).west(8, 32, 4, 26).up(49, 16, 45, 12).down(49, 16, 45, 20).east(8, 32, 4, 26)).armor(), PartPose.rotation(0.1309f, 0, 0));
        right_leg_shin.addOrReplaceChild("right_leg_shin_i0", GroupBuilder.create()
                .addBox(-2f, 0.3f, 1f, 4, 2, 4, new CubeUV().south(56, 2, 52, 0).north(55, 26, 51, 24).west(56, 16, 52, 14).up(40, 46, 36, 42).down(46, 38, 42, 42).east(55, 37, 51, 35)), PartPose.rotation(0.3491f, 0, 0));
        right_leg_shin.addOrReplaceChild("armor_right_leg_shin", GroupBuilder.create()
                .addBox(-2f, 0.3f, 1f, 4, 2, 4, new Vector3f(0.59f), new CubeUV().south(8, 29, 4, 25).west(8, 29, 4, 25).up(8, 29, 4, 25).down(49, 8, 45, 12).east(8, 29, 4, 25)).armor(), PartPose.rotation(0.3491f, 0, 0));
        right_leg.addOrReplaceChild("right_leg_thigh", GroupBuilder.create()
                .addBox(-2f, -8f, -2f, 4, 8, 4, new CubeUV().south(36, 8, 32, 0).north(32, 28, 28, 20).west(12, 40, 8, 32).up(27, 46, 23, 42).down(31, 42, 27, 46).east(32, 36, 28, 28)), PartPose.rotation(0.3491f, 0, 0));
        right_leg.addOrReplaceChild("armor_right_leg_thigh", GroupBuilder.create()
                .addBox(-2f, -8f, -2f, 4, 8, 4, new Vector3f(0.6f), new CubeUV().south(16, 29, 12, 20).north(8, 29, 4, 20).west(12, 29, 8, 20).up(8, 20, 4, 16).down(8, 25, 4, 29).east(12, 29, 8, 20)).armor(), PartPose.rotation(0.3491f, 0, 0));
        GroupDefinition left_leg = root.addOrReplaceChild("left_leg", GroupBuilder.create(), PartPose.offsetAndRotation(-2, 13, 0, 0f, 0.1309f, -0.1309f));
        GroupDefinition left_leg_shin = left_leg.addOrReplaceChild("left_leg_shin", GroupBuilder.create(), PartPose.offset(0, -6, -2));
        GroupDefinition left_leg_ = left_leg_shin.addOrReplaceChild("left_leg_", GroupBuilder.create(), PartPose.offset(0, -1, 3));
        GroupDefinition left_foot = left_leg_.addOrReplaceChild("left_foot", GroupBuilder.create());
        left_foot.addOrReplaceChild("left_foot_i0", GroupBuilder.create()
                .addBox(-2.7f, -6f, -3.5f, 4f, 2, 5, new CubeUV().south(8, 55, 4, 53).north(57, 4, 53, 2).west(53, 44, 48, 42).up(44, 5, 40, 0).down(8, 40, 4, 45).east(53, 35, 48, 33))
                .addMesh(new float[]{0.915f, -6.025f, 1.025f, 0.725f, -6.025f, -1.325f, -2.315f, -6.025f, 1.025f, -2.125f, -6.025f, -1.325f, -0.7f, -6.025f, -0.3f, 1.2f, -6.025f, 0, -0.7f, -6.025f, 0.9f, -2.6f, -6.025f, 0, -0.7f, -6.025f, -1.8f, 0.5f, -6.025f, -2.5f, 0.7071f, -6.025f, -2, 1, -6.025f, -2.2929f, 1, -6.025f, -2.7071f, 0.7071f, -6.025f, -3, 0.2929f, -6.025f, -3, 0f, -6.025f, -2.7071f, 0, -6.025f, -2.2929f, 0.2929f, -6.025f, -2, -0.7f, -6.025f, -2.5f, -0.4929f, -6.025f, -2, -0.2f, -6.025f, -2.2929f, -0.2f, -6.025f, -2.7071f, -0.4929f, -6.025f, -3, -0.9071f, -6.025f, -3, -1.2f, -6.025f, -2.7071f, -1.2f, -6.025f, -2.2929f, -0.9071f, -6.025f, -2, -1.9f, -6.025f, -2.5f, -1.6929f, -6.025f, -2, -1.4f, -6.025f, -2.2929f, -1.4f, -6.025f, -2.7071f, -1.6929f, -6.025f, -3, -2.1071f, -6.025f, -3, -2.4f, -6.025f, -2.7071f, -2.4f, -6.025f, -2.2929f, -2.1071f, -6.025f, -2}, new float[]{8, 57.1713f, 27.5f, 1, 58.6529f, 27.2531f, 5, 59.3287f, 26.0184f, 4, 57.4052f, 26.0184f, 5, 59.9618f, 58, 0, 59.8401f, 57.1284f, 6, 58.2254f, 57, 4, 58.0382f, 58, 6, 41.3427f, 59.8128f, 2, 41.2142f, 58.1981f, 7, 40.1573f, 58.0765f, 4, 40.1573f, 60, 7, 43, 58.3425f, 3, 42.2469f, 59.0184f, 8, 42, 60.5f, 4, 43, 60.2661f, 11, 57.6121f, 59.3827f, 12, 57.7706f, 59, 9, 57.2294f, 59, 10, 57.2294f, 59.5412f, 13, 59.612f, 55.3827f, 14, 59.7706f, 55, 9, 59.2294f, 55, 12, 59.2294f, 55.5412f, 15, 50.1585f, 16.1585f, 16, 50, 17, 9, 51, 17, 14, 51, 16, 9, 22, 56, 16, 21, 56, 17, 21.1585f, 56.3827f, 10, 22, 56.5412f, 20, 59.6121f, 59.3827f, 21, 59.7706f, 59, 18, 59.2294f, 59, 19, 59.2294f, 59.5412f, 22, 59.6121f, 39.1585f, 23, 59.2294f, 39, 18, 59.2294f, 40, 21, 59.7706f, 40, 24, 53.6121f, 59.3827f, 25, 53.7706f, 59, 18, 53.2294f, 59, 23, 53.2294f, 59.5412f, 18, 53, 3, 25, 53, 2, 26, 52.1585f, 2.1585f, 19, 52, 3, 29, 7.1585f, 51.1585f, 30, 7, 52, 27, 8, 52, 28, 8, 51, 31, 12.1585f, 54.1585f, 32, 12, 55, 27, 13, 55, 30, 13, 54, 33, 25.1585f, 50.1585f, 34, 25, 51, 27, 26, 51, 32, 26, 50, 27, 33, 56, 34, 32, 56, 35, 32.1586f, 56.3827f, 28, 33, 56.5412f}), PartPose.rotation(0, 0, 0.1309f));
        left_foot.addOrReplaceChild("armor_left_foot", GroupBuilder.create()
                .addBox(-2.7f, -6f, -3.5f, 4f, 2, 5, new Vector3f(0.6f), new CubeUV().south(12, 20, 8, 16).north(8, 20, 12, 16).west(12, 16, 8, 20).up(12, 20, 8, 16).down(8, 16, 12, 20).east(12, 20, 8, 16)).armor(), PartPose.rotation(0, 0, 0.1309f));
        left_leg_.addOrReplaceChild("left_leg__i0", GroupBuilder.create()
                .addBox(-2f, -5f, -2f, 4, 6, 4, new CubeUV().south(40, 32, 36, 26).north(40, 26, 36, 20).west(32, 42, 28, 36).up(35, 48, 31, 44).down(48, 42, 44, 46).east(28, 42, 24, 36)), PartPose.rotation(0.1309f, 0, 0));
        left_leg_.addOrReplaceChild("armor_left_leg_", GroupBuilder.create()
                .addBox(-2f, -5f, -2f, 4, 6, 4, new Vector3f(0.6f), new CubeUV().south(4, 32, 0, 26).north(0, 32, 4, 26).west(4, 32, 8, 26).up(8, 50, 4, 46).down(27, 46, 23, 50).east(8, 32, 4, 26)).armor(), PartPose.rotation(0.1309f, 0, 0));
        left_leg_shin.addOrReplaceChild("left_leg_shin_i0", GroupBuilder.create()
                .addBox(-2f, 0.3f, 1f, 4, 2, 4, new CubeUV().south(55, 54, 51, 52).north(56, 33, 52, 31).west(4, 55, 0, 53).up(44, 46, 40, 42).down(47, 16, 43, 20).east(49, 54, 45, 52)), PartPose.rotation(0.3491f, 0, 0));
        left_leg_shin.addOrReplaceChild("armor_left_leg_shin", GroupBuilder.create()
                .addBox(-2f, 0.3f, 1f, 4, 2, 4, new Vector3f(0.59f), new CubeUV().south(8, 29, 4, 25).north(53, 26, 49, 24).west(4, 29, 8, 25).up(8, 29, 4, 25).down(4, 46, 0, 50).east(8, 29, 4, 25)).armor(), PartPose.rotation(0.3491f, 0, 0));
        left_leg.addOrReplaceChild("left_leg_thigh", GroupBuilder.create()
                .addBox(-2f, -8f, -2f, 4, 8, 4, new CubeUV().south(36, 36, 32, 28).north(16, 40, 12, 32).west(40, 8, 36, 0).up(46, 34, 42, 30).down(46, 34, 42, 38).east(36, 28, 32, 20)), PartPose.rotation(0.3491f, 0, 0));
        left_leg.addOrReplaceChild("armor_left_leg_thigh", GroupBuilder.create()
                .addBox(-2f, -8f, -2f, 4, 8, 4, new Vector3f(0.6f), new CubeUV().south(16, 29, 12, 20).north(4, 29, 8, 20).west(12, 29, 8, 20).up(8, 20, 4, 16).down(4, 25, 8, 29).east(12, 29, 8, 20)).armor(), PartPose.rotation(0.3491f, 0, 0));

        return ModelDefinition.create(modelBuilder, 128, 128, 2);
    }
}