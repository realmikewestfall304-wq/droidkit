/*
 * Copyright 2012-2014 Daniel Serdyukov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lightydev.dk.app;

import android.content.Intent;
import android.net.Uri;

/**
 * Utility class for creating intents to interact with Telegram messenger.
 * Provides methods for common Telegram interactions such as opening profiles,
 * chats, sharing content, and joining channels.
 *
 * @since 2.2.1
 */
public final class TelegramIntents {

  private static final String TELEGRAM_PACKAGE = "org.telegram.messenger";
  private static final String TELEGRAM_URL_SCHEME = "tg://";

  private TelegramIntents() {
  }

  /**
   * Creates an intent to launch the Telegram app.
   *
   * @return Intent to launch Telegram
   */
  public static Intent launch() {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setPackage(TELEGRAM_PACKAGE);
    return intent;
  }

  /**
   * Creates an intent to open a user's profile or chat in Telegram.
   * This opens the conversation thread with the specified user.
   *
   * @param username the Telegram username (without @ symbol)
   * @return Intent to open the user's profile/chat
   */
  public static Intent profile(String username) {
    return new Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_URL_SCHEME + "resolve?domain=" + username));
  }

  /**
   * Creates an intent to open a chat with a user in Telegram.
   * This is an alias for profile() as Telegram treats them identically.
   *
   * @param username the Telegram username (without @ symbol)
   * @return Intent to open a chat with the user
   */
  public static Intent chat(String username) {
    return profile(username);
  }

  /**
   * Creates an intent to open a chat with a user and pre-filled message text.
   *
   * @param username the Telegram username (without @ symbol)
   * @param text the pre-filled message text
   * @return Intent to open a chat with pre-filled text
   */
  public static Intent chat(String username, String text) {
    return new Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_URL_SCHEME + "resolve?domain=" + username + "&text=" + Uri.encode(text)));
  }

  /**
   * Creates an intent to share text content via Telegram.
   * This opens the Telegram share dialog to select a chat to share with.
   *
   * @param text the text content to share
   * @return Intent to share content via Telegram
   */
  public static Intent share(String text) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.setPackage(TELEGRAM_PACKAGE);
    intent.putExtra(Intent.EXTRA_TEXT, text);
    return intent;
  }

  /**
   * Creates an intent to open a bot with optional start parameters.
   *
   * @param botUsername the bot's username (without @ symbol)
   * @param startParam optional start parameter for the bot
   * @return Intent to open the bot
   */
  public static Intent bot(String botUsername, String startParam) {
    String url = TELEGRAM_URL_SCHEME + "resolve?domain=" + botUsername;
    if (startParam != null && !startParam.isEmpty()) {
      url += "&start=" + Uri.encode(startParam);
    }
    return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
  }

  /**
   * Creates an intent to open a bot.
   *
   * @param botUsername the bot's username (without @ symbol)
   * @return Intent to open the bot
   */
  public static Intent bot(String botUsername) {
    return bot(botUsername, null);
  }

  /**
   * Creates an intent to join a channel via invite link.
   *
   * @param inviteHash the channel invite hash (from the invite link)
   * @return Intent to join the channel
   */
  public static Intent joinChannel(String inviteHash) {
    return new Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_URL_SCHEME + "join?invite=" + inviteHash));
  }

}
