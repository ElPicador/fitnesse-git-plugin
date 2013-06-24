package org.fitnesse.plugins;

import fitnesse.ComponentFactory;
import fitnesse.testsystems.CommandRunner;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;


public class GitDelegate {

    private String MACHINE_NAME = null;
    protected String COMMIT_TOKEN = null;
    private CommandExecutor executor = new CommandExecutor();

    public void update(String file) throws Exception {
        executor.exec(gitPath() + " add " + file);
    }

    public void delete(String file) throws Exception {
        executor.exec(gitPath() + " rm -rf --cached " + file);
    }

    public void commit() throws Exception {
        executor.exec(gitPath() + " commit -am " + commitMessage());
    }

    public void push() throws Exception {
      executor.exec(gitPath() + " push");
    }


    protected String commitMessage() {
        return "FitNesse";
    }

    private String freshToken() {
        return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(new Date());
    }

    protected String gitPath() {
        try {
            FileInputStream inputStream = new FileInputStream(ComponentFactory.PROPERTIES_FILE);
            Properties properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
            String gitPath = properties.getProperty("git.path");
            return gitPath != null ? gitPath : "/usr/local/bin/git";
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
