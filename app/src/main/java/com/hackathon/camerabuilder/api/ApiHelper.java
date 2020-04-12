package com.hackathon.camerabuilder.api;

public class ApiHelper {
    public final static String host = "https://192.168.0.101:3000/";
    private final static String BASE_URL = host + "api/";
    final static String LOGIN = BASE_URL + "Login";
    final static String REGISTER = BASE_URL + "Register";
    final static String GET_ALL_CAMS = BASE_URL + "cameras";
    final static String GET_ALL_LENSES = BASE_URL + "lenses";
    final static String GET_LENS = BASE_URL + "lens";
    final static String GET_ALL_ADAPTERS = BASE_URL + "adapters";
    final static String GET_ADAPTER = BASE_URL + "adapter";
    final static String GET_ALL_FLASHES = BASE_URL + "flashes";
    final static String GET_FLASH = BASE_URL + "flash";
    final static String GET_CAM = BASE_URL + "camera";
    final static String ADD_TO_BAG = BASE_URL + "addToBag";
    final static String CREATE_KIT = BASE_URL + "createKit";
    final static String GET_ALL_KITS = BASE_URL + "findUserKits";
    final static String Find_Compatible_Adapters = BASE_URL + "findCompatibleAdapters";
}
