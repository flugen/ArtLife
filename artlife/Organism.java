package artlife;

import java.awt.Color;
import java.awt.Graphics2D;



public class Organism extends Gridy{
    
    private Color c;
    protected int age;
    private direction dir;
    private double maxE,energy;
    private boolean poisoned,flashing,flash;
    private Gridy lts;
    private DNA dna;
    private travel mode;
    private int count=0;
    
    public Organism(int x, int y){
    	super(x,y);
        c = Color.red;
        dir = direction.UP;
        mode = travel.WALK;
        energy = maxE = 500;
        dna = DNA.makeDefault();
        age = 0;
    }

	public Organism(int x, int y, Color c, travel m, double mE, DNA dna) {
		super(x,y);
		this.c = c;
		dir = direction.UP;
		mode = m;
		energy = maxE = mE;
		this.dna = dna;
		age = 0;
	}

	@Override
	public void draw(Graphics2D g, int size) {
		if(flashing && flash) {
			flash = !flash;
			g.setColor(Color.white);
		}else {
			if(flashing)
				flash = !flash;
			g.setColor(c);
		}
		int s2 = size/2;
		g.drawRect(x*size+s2/2, y*size+s2/2, s2, s2);
	}
    
	public void update(Grid grid) {
		energy--;
		age++;
		if(poisoned) {
			energy -= 2;
			count++;
			if(count%10==0){
				count = 0;
				poisoned = false;
			}
		}
		ping(grid);
		dna.performNextBehavior(grid, this);
	}

	public void ping(Grid grid) {
		lts=null;
		for(int i=1;i<=4&&lts==null;i++) {
			lts = grid.thingAt(x+dir.dx*i, y+dir.dy*i);
		}
		if(lts==null) {
			lts = grid.thingAt(x+dir.dx*4+dir.CW().dx, y+dir.dy*4+dir.CW().dy);
		}
		if(lts==null) {
			lts = grid.thingAt(x+dir.dx*4+dir.CCW().dx, y+dir.dy*4+dir.CCW().dy);
		}
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

	public boolean isFlashing() {
		return flashing;
	}

	public void setFlashing(boolean flashing) {
		this.flashing = flashing;
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

	public DNA getDNA() {
		return dna;
	}
    
}