package net.kunmc.lab.panelcraft;

public class StepOnGame extends Game {

    public StepOnGame(int x, int y, int z) {
        super(x, y, z);
    }

    @Override
    public void run() {

    }

    @Override
    public PanelMode getMode() {
        return PanelMode.StepOn;
    }

}
