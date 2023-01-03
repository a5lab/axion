package com.a5lab.tabr.utils;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum FlashMessages {
  INFO("msg_info"),
  ERROR("msg_error"),
  WARNING("msg_warning");

  private final String text;
}
