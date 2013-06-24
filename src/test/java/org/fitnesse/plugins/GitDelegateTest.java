package org.fitnesse.plugins;

import fitnesse.testsystems.CommandRunner;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.InetAddress;
import java.text.DateFormat;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class GitDelegateTest {

    @Mock
    private CommandExecutor executor;

    @InjectMocks
    private GitDelegate gitDelegate = new GitDelegate() {
        @Override
        protected String gitPath() {
            return "git";
        }

        @Override
        protected String commitMessage() {
            return "message";
        }
    };

    @Test
    public void update() throws Exception {
        gitDelegate.update("content.txt");
        verify(executor).exec("git add content.txt");
    }

    @Test
    public void commit() throws Exception {
        CommandRunner mockCommandRunner = Mockito.mock(CommandRunner.class);
        when(mockCommandRunner.getOutput()).thenReturn("something else");
        gitDelegate.commit();
        verify(executor).exec("git commit -am message");
    }


    @Test
    public void delete() throws Exception {
        gitDelegate.delete("properties.xml");
        verify(executor).exec("git rm -rf --cached properties.xml");
    }



    @Test
    public void commitMessage_forAmend() throws Exception {
        GitDelegate gd = new GitDelegate();

        String firstCommitMessage = gd.commitMessage();
        String amendMessage = gd.commitMessage();

        assertTrue(firstCommitMessage.contains("FitNesse"));
        assertEquals(firstCommitMessage, amendMessage);
    }

}
