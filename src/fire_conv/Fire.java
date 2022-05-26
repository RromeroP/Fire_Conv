/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fire_conv;

/**
 *
 * @author Rex
 */
public final class Fire {

    boolean running;
    boolean paused;

    int fps;
    Thread fireThread;

    public Fire() {
        newThread();
    }

    public void setFps(int fps) {
        this.fps = fps;
        System.out.println(fps);
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void fireSim() {
        
    }
    
    

    public void newThread() {
        fireThread = new Thread(() -> {
            while (running) {
                try {
                    if (!paused) {
                        //FIRE HERE
                    }
                    try {
                        Thread.sleep(1000 / fps);
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                } catch (Exception ex) {
                }
            }
        });
    }

}
