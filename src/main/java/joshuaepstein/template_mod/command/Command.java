package joshuaepstein.template_mod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

public abstract class Command {

    public abstract String getName();

    public abstract int getRequiredPermissionLevel();

    public abstract void build(LiteralArgumentBuilder<CommandSourceStack> builder);

    public abstract boolean isDedicatedServerOnly();

    protected final void sendFeedback(CommandContext<CommandSourceStack> context, String message, boolean showOps) {
        context.getSource().sendSuccess(new TextComponent(message), showOps);
    }
    public void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher, String literal) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal(this.getName());
        builder.requires((sender) -> {
            return sender.hasPermission(this.getRequiredPermissionLevel());
        });
        this.build(builder);
        dispatcher.register(Commands.literal(literal).then(builder));
    }
}