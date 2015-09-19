package ssh.schell.commands;

import com.jcraft.jsch.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 18.09.15.
 */
public class Ls extends Command {

    private List<String> arguments = new ArrayList<>();

    public Ls(Session session) {
        super(session);
    }

    @Override
    protected String createCommand() {
        StringBuilder stringBuilder = new StringBuilder("ls");
        for(String arg : arguments) {
            stringBuilder.append(" -" + arg);
        }
        return stringBuilder.toString();
    }

    @Override
    public String executeCommand() {
        String result = super.executeCommand();
        return result;
        //return "worked. now format it!";
    }

    @Override
    public void addArgument(String argument) {
        arguments.add(argument);
    }

    @Override
    public void clearArguments() {
        arguments = new ArrayList<>();
    }

}
