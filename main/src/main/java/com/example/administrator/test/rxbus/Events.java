package com.example.administrator.test.rxbus;

import co.bitpartner.data.model.AssetRow;

/**
 * Created by Administrator on 2018-02-28.
 */

public class Events {

    public static class AssetEvent {
        private String tag;
        private AssetRow row;
        public AssetEvent(AssetRow row, String tag) {
            this.tag = tag;
            this.row = row;
        }
        public String getTag() {
            return tag;
        }
        public AssetRow getRow() {
            return row;
        }
    }

    public static class NetworkChangeEvent {

    }

    public static class FcmLevelEvent {
        private String tag;
        public FcmLevelEvent(String level) {
            tag = level;
        }
        public String getTag() {
            return tag;
        }
    }

    public static class DialogFloatEvent {
        private String tag;
        public DialogFloatEvent(){}
        public String getTag() {
            return tag;
        }
    }

    public static class ErrorCodeEvent {
        private String code;
        public ErrorCodeEvent(String code) {
            this.code = code;
        }
        public String getCode() {
            return code;
        }
    }

    public static class MarketChangeEvent {

    }

    public static class DataSetEvent {
    }
}
