/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icee.myth.network.message;

/**
 *
 * @author lidonglin
 */
public class Message {
    public int id;
    public Object content;

    public Message(int id, Object content) {
        this.id = id;
        this.content = content;
    }
}
