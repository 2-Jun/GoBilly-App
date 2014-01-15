package com.TwoJun.gobillyapp;

/**
 * An abstract class that suggests that a word dispatcher in this app is supposed to be.
 *
 * @author jyp <interruptz@gmail.com>
 * @since 01/16/2014
 */

public abstract class WordDispatcher {
    public abstract void update();
    public abstract String getWord();
    public abstract String getDescription();
    public abstract String getExample();

}
