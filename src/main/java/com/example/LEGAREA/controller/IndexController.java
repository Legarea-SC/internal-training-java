package com.example.LEGAREA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/***
 * 本システムのメイン画面表示時に動作するコントローラクラス
 */
@Controller("/")
public class IndexController {

    /***
     * メイン画面の初期表示用コントローラクラス
     * @return　index.htmlのパス
     */
    @GetMapping
    public String index() {
        return "index";
    }

}

