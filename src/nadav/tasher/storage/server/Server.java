package nadav.tasher.storage.server;

import nadav.tasher.storage.area.Area;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public abstract class Server {

    private static final Queue<Operation> queue = new ArrayDeque<>();

    private static final ArrayList<Area> areas = new ArrayList<>();

    public static void initialize(){

    }

}
