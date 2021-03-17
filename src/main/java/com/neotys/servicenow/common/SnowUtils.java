package com.neotys.servicenow.common;

import javax.swing.*;
import java.net.URL;

public class SnowUtils {

    private static final ImageIcon Snow_ICON;
    private static final String IMAGE_NAME="service-now-18px.png";
    public static final String ARTIFACT="Artifact";

    static {
        final URL iconURL = SnowUtils.class.getResource(IMAGE_NAME);
        if (iconURL != null) {
            Snow_ICON = new ImageIcon(iconURL);
        } else {
            Snow_ICON = null;
        }
    }

    public SnowUtils() {
    }

    public static ImageIcon getSnow_ICON() {
        return Snow_ICON;
    }
}
