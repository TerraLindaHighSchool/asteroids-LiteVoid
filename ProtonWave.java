import greenfoot.*;

/**
 * A proton wave that expands and destroys asteroids in its path.
 * 
 * @author Ryan Hoang
 * @version 1.2
 */
public class ProtonWave extends Actor
{
    /** The damage this wave will deal */
    private static final int DAMAGE = 30;
    
    /** How many images should be used in the animation of the wave */
    private static final int NUMBER_IMAGES= 30;
   
    /** 
     * The images of the wave. This is static so the images are not
     * recreated for every object (improves performance significantly).
     */
    private static GreenfootImage[] images;
    
    private int currentImage = 0;
    
    private int life = 29;
    
    /**
     * Create a new proton wave.
     */
    public ProtonWave() 
    {
        setImage(images[0]); 
        initializeImages();
        currentImage = 0;
        Greenfoot.playSound("proton.wav");
    }
    
    /** 
     * Create the images for expanding the wave.
     */
    public static void initializeImages() 
    {
        if(images == null) 
        {
            GreenfootImage baseImage = new GreenfootImage("wave.png");
            images = new GreenfootImage[NUMBER_IMAGES];
            for (int i = 0; i < NUMBER_IMAGES; i++) 
            {
                int size = (i + 1) * ( baseImage.getWidth() / NUMBER_IMAGES );
                images[i] = new GreenfootImage(baseImage);
                images[i].scale(size, size);
            }
        }
    }
    
    /**
     * Act for the proton wave is: grow and check whether we hit anything.
     */
    public void act()
    { 
        grow();
        if(life <= 0) {
            getWorld().removeObject(this);
        } 
        else 
        {
            life--;
            checkAsteroidHit();
        }
    }
    
    /**
     * Checks if it hits an Asteroid and deals damage to the Asteroid
     * It also increases the score based on the number of hits
     */
    private void checkAsteroidHit()
    {
        Asteroid asteroid = (Asteroid) getOneIntersectingObject(Asteroid.class);
        if (asteroid != null)
        {
            ((Space) getWorld()).updateScore(2);
            asteroid.hit(DAMAGE);
        }
    }
    
    private void grow()
    {
        if (currentImage < NUMBER_IMAGES)
        {
            setImage(images[currentImage]); 
            currentImage++;
        }
    }
}
