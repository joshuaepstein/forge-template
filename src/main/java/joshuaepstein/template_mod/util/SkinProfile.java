package joshuaepstein.template_mod.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.play.PlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.network.play.server.SPlayerListItemPacket;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class SkinProfile {

    public static final ExecutorService SERVICE = Executors.newFixedThreadPool(3);

    private String latestNickname;
    public AtomicReference<GameProfile> gameProfile = new AtomicReference<>();
    public AtomicReference<PlayerInfo> playerInfo = new AtomicReference<>();

    public String getLatestNickname(){
        return latestNickname;
    }

    public void updateSkin(String name) {
        if(name.equals(this.latestNickname))return;
        this.latestNickname = name;

        SERVICE.submit(() -> {
            gameProfile.set(new GameProfile(null, name));
            gameProfile.set(SkullTileEntity.updateGameprofile(gameProfile.get()));
            SPlayerListItemPacket.AddPlayerData data = new SPlayerListItemPacket().new AddPlayerData(gameProfile.get(), 0, null, null);
            playerInfo.set(new PlayerInfo(data));

        });
    }

    public ResourceLocation getLocationSkin() {
        if (this.playerInfo == null || this.playerInfo.get() == null) {
            return DefaultPlayerSkin.getDefaultSkin();
        }

        try {
            return this.playerInfo.get().getSkinLocation();
        } catch (Exception e) {
            System.err.println("stupid! how did you even do this?");
            e.printStackTrace();
        }

        return DefaultPlayerSkin.getDefaultSkin();
    }

    public static void updateGameProfile(GameProfile input, Consumer<GameProfile> consumer) {
        SERVICE.submit(() -> {
            GameProfile output = SkullTileEntity.updateGameprofile(input);
            consumer.accept(output);
        });
    }

}
