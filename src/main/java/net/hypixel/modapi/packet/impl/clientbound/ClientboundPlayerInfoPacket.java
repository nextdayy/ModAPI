package net.hypixel.modapi.packet.impl.clientbound;

import net.hypixel.data.rank.MonthlyPackageRank;
import net.hypixel.data.rank.PackageRank;
import net.hypixel.data.rank.PlayerRank;
import net.hypixel.modapi.handler.ClientboundPacketHandler;
import net.hypixel.modapi.packet.ClientboundHypixelPacket;
import net.hypixel.modapi.packet.impl.VersionedPacket;
import net.hypixel.modapi.serializer.PacketSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ClientboundPlayerInfoPacket extends ClientboundVersionedPacket {
    private static final int CURRENT_VERSION = 1;

    private PlayerRank playerRank;
    private PackageRank packageRank;
    private MonthlyPackageRank monthlyPackageRank;
    @Nullable
    private String prefix;

    public ClientboundPlayerInfoPacket(PlayerRank playerRank, PackageRank packageRank, MonthlyPackageRank monthlyPackageRank, @Nullable String prefix) {
        super(CURRENT_VERSION);
        this.playerRank = playerRank;
        this.packageRank = packageRank;
        this.monthlyPackageRank = monthlyPackageRank;
        this.prefix = prefix;
    }

    public ClientboundPlayerInfoPacket(PacketSerializer serializer) {
        super(serializer);
    }

    @Override
    protected boolean read(PacketSerializer serializer) {
        if (!super.read(serializer)) {
            return false;
        }

        this.playerRank = PlayerRank.getById(serializer.readVarInt()).orElseThrow(() -> new IllegalArgumentException("Invalid player rank ID"));
        this.packageRank = PackageRank.getById(serializer.readVarInt()).orElseThrow(() -> new IllegalArgumentException("Invalid package rank ID"));
        this.monthlyPackageRank = MonthlyPackageRank.getById(serializer.readVarInt()).orElseThrow(() -> new IllegalArgumentException("Invalid monthly package rank ID"));
        this.prefix = serializer.readOptionally(PacketSerializer::readString);
        return true;
    }

    @Override
    public void write(PacketSerializer serializer) {
        super.write(serializer);
        serializer.writeVarInt(playerRank.getId());
        serializer.writeVarInt(packageRank.getId());
        serializer.writeVarInt(monthlyPackageRank.getId());
        serializer.writeOptionally(prefix, PacketSerializer::writeString);
    }

    @Override
    protected int getLatestVersion() {
        return CURRENT_VERSION;
    }

    @Override
    public void handle(ClientboundPacketHandler handler) {
        handler.onPlayerInfoPacket(this);
    }

    public PlayerRank getPlayerRank() {
        return playerRank;
    }

    public PackageRank getPackageRank() {
        return packageRank;
    }

    public MonthlyPackageRank getMonthlyPackageRank() {
        return monthlyPackageRank;
    }

    public Optional<String> getPrefix() {
        return Optional.ofNullable(prefix);
    }

    @Override
    public String toString() {
        return "ClientboundPlayerInfoPacket{" +
                "playerRank=" + playerRank +
                ", packageRank=" + packageRank +
                ", monthlyPackageRank=" + monthlyPackageRank +
                ", prefix='" + prefix + '\'' +
                "} " + super.toString();
    }
}
