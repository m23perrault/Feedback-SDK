package com.feedbacksdk.beans;

import java.io.Serializable;

/**
 * Created by android-da on 1/10/19.
 */

/*
* Feedback Replies Model
* */
public class FeedbackReplyModel implements Serializable {
     int intChatType;
     String message;
     String sender_user;
     String created_on;

     public String getMessage() {
          return message;
     }

     public void setMessage(String message) {
          this.message = message;
     }

     public String getSender_user() {
          return sender_user;
     }

     public void setSender_user(String sender_user) {
          this.sender_user = sender_user;
     }

     public String getCreated_on() {
          return created_on;
     }

     public void setCreated_on(String created_on) {
          this.created_on = created_on;
     }

     public int getIntChatType() {
          return intChatType;
     }

     public void setIntChatType(int intChatType) {
          this.intChatType = intChatType;
     }
}
