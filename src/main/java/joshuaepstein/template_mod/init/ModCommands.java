package joshuaepstein.template_mod.init;

import joshuaepstein.template_mod.command.Command;
import com.mojang.brigadier.CommandDispatcher;
import joshuaepstein.template_mod.Main;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.function.Supplier;

public class ModCommands {
//    public static Command COMMAND;

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection env){
//        COMMAND = registerCommand(CommandCommand::new, dispatcher, env);

        Main.LOGGER.info("Template Mod | Commands are loaded successfully!");
    }

    public static <T extends Command> T registerCommand(Supplier<T> supplier, CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection env){
        Command command = supplier.get();
        if(!command.isDedicatedServerOnly() || env == Commands.CommandSelection.DEDICATED || env == Commands.CommandSelection.ALL){
            command.registerCommand(dispatcher, Main.MOD_ID);
        }
        return (T) command;
    }
}
