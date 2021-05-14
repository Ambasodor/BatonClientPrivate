package haven.automation;

import haven.Coord;
import haven.Coord2d;
import haven.GameUI;
import haven.Gob;
import haven.Loading;
import haven.Resource;

import java.util.ArrayList;
import java.util.Arrays;

import static haven.OCache.posres;

public class GoIn implements Runnable {

    private GameUI gui;


    public GoIn(GameUI gui) {
        this.gui = gui;
    }

    @Override
    public void run() {
        Gob vehicle = null;
        synchronized (gui.map.glob.oc) {
            for (Gob gob : gui.map.glob.oc) {
                try {
                    Resource res = gob.getres();
                    if (res != null && (res.name.startsWith("gfx/terobjs/vehicle/rowboat") ||
                            res.name.startsWith("gfx/terobjs/vehicle/dugout") ||
                            res.name.startsWith("gfx/terobjs/vehicle/spark")))
                    {
                        Coord2d plc = gui.map.player().rc;
                        if ((vehicle == null || gob.rc.dist(plc) < vehicle.rc.dist(plc)) && vehicle == null)
                            vehicle = gob;
                    }
                } catch (Loading l) {
                }
            }
        }

        if (vehicle == null)
            return;

        gui.map.wdgmsg("click", vehicle.sc, vehicle.rc.floor(posres), 3, 0, 0, (int) vehicle.id, vehicle.rc.floor(posres), 0, -1);
    }

}