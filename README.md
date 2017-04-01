# VimFight

![vimFight snapshot lost](https://raw.github.com/Rileghft/VimFight/master/snapshot/vimFight.PNG)

## 簡介

VimFight是一款Vim教學遊戲，在遊戲中玩家要在程式碼地圖中生存並賺取積分與魔力，隨著分數的累積，地圖上會出現陷阱與補品，玩家必須閃避陷阱、保持移動、補充體力與魔力，直到最後玩家死亡。
![vimFight snapshot lost](https://raw.github.com/Rileghft/VimFight/master/snapshot/game.PNG)

## 操控

![vimFight snapshot lost](https://raw.github.com/Rileghft/VimFight/master/snapshot/instruction.PNG)

支援的Vim操作如下:

`k` 上 `j` 下 `h` 左 `l` 右

`0` 行頭 `$`行尾

`w` 下一個詞 `b` 詞開頭

`f` 向前搜尋字元 `F`向後搜尋字元

`dd` 刪除所在列上陷阱(消耗200魔力)

以上指令皆可以配合數字連續施放(如: 3dd)

`:<NUM>` 移動到指定列(消耗300魔力)

`:h` 幫助訊息

`:p` 暫停

`:q` 離開遊戲

## 如何建置

本專案使用libGDX遊戲函式庫，需使用gradle建置，詳細請參閱Wiki [如何建置專案](https://github.com/Rileghft/VimFight/wiki/%E5%A6%82%E4%BD%95%E5%BB%BA%E7%BD%AE%E5%B0%88%E6%A1%88)