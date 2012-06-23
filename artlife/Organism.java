package artlife;

import java.awt.Color;
import java.awt.Graphics2D;



public class Organism extends Gridy{
    
    private Color c;
    private direction dir;
	private double maxE,energy;
    private boolean poisoned;
    private Gridy lts;
    private DNA dna;
    private travel mode;
    
    
    public Organism(int x, int y){
    	super(x,y);
        c = Color.red;
        dir = direction.UP;
        mode = travel.WALK;
        energy = maxE = 500;
        dna = DNA.makeDefault();
    }

	@Override
	public void draw(Graphics2D g, int size) {
		g.setColor(c);
		g.drawRect(x*size+1, y*size+1, size/2, size/2);
	}
    
	public void update(Grid grid) {
		energy--;
		for(int i=1;i<=4&&lts==null;i++) {
			lts = grid.thingAt(x+dir.dx*i, y+dir.dy*i);
		}
		if(lts==null) {
			lts = grid.thingAt(x+dir.dx*4+dir.CW().dx, y+dir.dy*4+dir.CW().dy);
		}
		if(lts==null) {
			lts = grid.thingAt(x+dir.dx*4+dir.CCW().dx, y+dir.dy*4+dir.CCW().dy);
		}
		dna.performNextBehavior(grid, this);
	}

	public direction getDir() {
		return dir;
	}

	public void setDir(direction dir) {
		this.dir = dir;
	}

	public void feed(double amt) {
		energy=Math.min(energy + amt, maxE);
	}
	
	public double getEnergy() {
		return energy;
	}

	public boolean isGone() {return energy<=0;}
	
	public double getMaxE() {
		return maxE;
	}

	public Color getC() {
		return c;
	}

	public boolean isPoisoned() {
		return poisoned;
	}

	public Gridy getLts() {
		return lts;
	}
	
	public travel getMode() {
		return mode;
	}

	public boolean sameSpecies(Organism other) {
		return Math.abs(other.c.getRed()-c.getRed())<.1 &&
				 Math.abs(other.c.getBlue()-c.getBlue())<.1 &&
				 Math.abs(other.c.getGreen()-c.getGreen())<.1 &&
				 Math.abs(other.c.getAlpha()-c.getAlpha())<.1;
	}
    
}