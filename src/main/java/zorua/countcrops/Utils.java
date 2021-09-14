package zorua.countcrops;

import org.bukkit.Location;

public class Utils {
    // From this thread
    // https://www.spigotmc.org/threads/rounding-coordinates.282050/?__cf_chl_jschl_tk__=pmd_HZrv37D96IUcDax7ewjegCXux_QwLuJS9A3.3YZmjqo-1631625657-0-gqNtZGzNAfujcnBszQhl
    public static Location center(Location location) {
        String x = "" + location.getX();
        String y = "" + location.getY();
        String z = "" + location.getZ();
        if(x.contains(".")) x = x.substring(0, x.indexOf("."));
        if(y.contains(".")) y = y.substring(0, y.indexOf("."));
        if(z.contains(".")) z = z.substring(0, z.indexOf("."));
        x+=".5";
        z+=".5";
        location.setX(Double.parseDouble(x));
        location.setY(Double.parseDouble(y));
        location.setZ(Double.parseDouble(z));
        return resetRotation(location);
    }

    public static Location resetRotation(Location location) {
        location.setYaw(0f);
        location.setPitch(0f);
        return location;
    }
}
