package com.asha.nightowllib.handler;

import android.view.View;

import com.asha.nightowllib.handler.annotations.OwlHandle;
import com.asha.nightowllib.handler.impls.ViewHandler;

import java.util.HashMap;

import static com.asha.nightowllib.NightOwlUtil.newInstanceSafely;

/**
 * Created by hzqiujiadi on 15/11/8.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class HandlerManager {
    private static HashMap<Class,ISkinHandler> sHandlers = new HashMap<>();
    private static HashMap<Class,Class<? extends ISkinHandler>> sHandlerTable = new HashMap<>();

    // TODO: 15/11/5 impl it later.
    private static Class<ISkinHandler> generateHandler() {
        return null;
    }

    public static void registerView(Class<? extends View> clz){
        sHandlerTable.put(clz, generateHandler());
    }

    public static void registerHandler(Class key, Class<? extends ISkinHandler> clz){
        sHandlerTable.put(key,clz);
    }

    public static void registerHandler(Class<? extends ISkinHandler> clz){
        OwlHandle owlHandle = clz.getAnnotation(OwlHandle.class);
        if ( owlHandle != null ){
            Class<? extends View> key = owlHandle.value();
            registerHandler(key,clz);
        }
    }

    public static ISkinHandler queryHandler(Class clz) {
        Class<? extends ISkinHandler> handlerClz = sHandlerTable.get(clz);
        if ( handlerClz == null ) handlerClz = ViewHandler.class;
        ISkinHandler skinHandler = sHandlers.get(handlerClz);
        if ( skinHandler == null ) {
            skinHandler = newInstanceSafely(handlerClz);
            sHandlers.put(handlerClz, skinHandler);
        }
        return skinHandler;
    }

}