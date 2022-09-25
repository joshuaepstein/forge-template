package joshuaepstein.template_mod.util;

import net.minecraft.util.Tuple;
import net.minecraftforge.event.TickEvent;

import java.util.Iterator;
import java.util.LinkedList;

public class ServerScheduler {
  public static final ServerScheduler INSTANCE = new ServerScheduler();

  private static final Object lock = new Object();

  private boolean inTick = false;

  private final LinkedList<Tuple<Runnable, Counter>> queue = new LinkedList<>();

  private final LinkedList<Tuple<Runnable, Integer>> waiting = new LinkedList<>();

  public void onServerTick(TickEvent.ServerTickEvent event) {
    if (event.phase == TickEvent.Phase.START)
      return;
    this.inTick = true;
    synchronized (lock) {
      this.inTick = true;
      Iterator<Tuple<Runnable, Counter>> iterator = this.queue.iterator();
      while (iterator.hasNext()) {
        Tuple<Runnable, Counter> r = iterator.next();
        r.getB().decrement();
        if (r.getB().getValue() <= 0) {
          r.getA().run();
          iterator.remove();
        }
      }
      this.inTick = false;
      for (Tuple<Runnable, Integer> wait : this.waiting)
        this.queue.addLast(new Tuple(wait.getA(), new Counter(wait.getB())));
    }
    this.waiting.clear();
  }

  public void schedule(int tickDelay, Runnable r) {
    synchronized (lock) {
      if (this.inTick) {
        this.waiting.addLast(new Tuple(r, Integer.valueOf(tickDelay)));
      } else {
        this.queue.addLast(new Tuple(r, new Counter(tickDelay)));
      }
    }
  }
}
