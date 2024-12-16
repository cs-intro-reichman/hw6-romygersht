import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);
		
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		// Reads the RGB values from the file into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		//// Replace the following statement with your code.
		/// 	for (int i = 0; i < numRows; i ++) {
		for (int i = 0; i < numRows; i ++) {
			for (int j = 0; j < numCols; j++) {
			int r = in.readInt(); 
			int g = in.readInt();
			int b = in.readInt();
			image [i][j] = new Color (r, g, b);
			}
		}
		return image;
		
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		int rows = image.length;
		int columns = image[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
                 print(image[i][j]);
			}
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] horizontalImage = new Color[image.length][image[0].length];
		int rows = image.length;
		int columns = image[0].length;
	
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				int c = columns - 1 - j; 
				horizontalImage[i][j] = image[i][c];
			}
		}
		return horizontalImage;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		Color[][] verticalImage = new Color[image.length][image[0].length];
		int rows = image.length;
		int columns = image[0].length;
	
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				int r = rows - 1 - j; 
				verticalImage[j][i] = image[r][i];
			}
		}
		return verticalImage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		int r = pixel.getRed();
		int g = pixel.getGreen();
		int b = pixel.getBlue();
	
		// Calculate luminance using the given formula
		double lum = 0.299 * r + 0.587 * g + 0.114 * b;
	
		// Create a new color with all components equal to the luminance
		return new Color((int) lum, (int) lum, (int) lum);
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		int rows = image.length;
		int columns = image[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
                 image[i][j] = luminance(image [i][j]);
			}
		}
		return image;
		
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		
		int originalWidth = image.length;
		int originalHeight = image[0].length;
	
		// Calculate scale ratio
		double widthR = (double) width / originalWidth;
		double heightR = (double) height / originalHeight;
	
		Color[][] newImage = new Color[width][height];
	
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int originalW = (int) (i / widthR); // Calculate the new index for the pixels
				int originalH = (int) (j / heightR);
	
				newImage[i][j] = image[originalW][originalH];
			}
		}
		return newImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int r1 = c1.getRed();
		int g1 = c1.getGreen();
		int b1 = c1.getBlue();
	
		int r2 = c2.getRed();
		int g2 = c2.getGreen();
		int b2 = c2.getBlue();
	
		int r = (int) (alpha * r1 + (1 - alpha) * r2);
		int g = (int) (alpha * g1 + (1 - alpha) * g2);
		int b = (int) (alpha * b1 + (1 - alpha) * b2);
	
		return new Color(r, g, b);
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {

		int width = image1.length; 
		int height = image1[0].length;
	
		Color[][] newImage = new Color[width][height];
	
	
		for (int i = 0; i < newImage.length; i ++) {
			for (int j = 0; j < newImage[i].length; j ++) {
	
			 newImage [i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
		return newImage;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		
		   // Scale the target image to match the source image's dimensions
		   target = scaled(target, source.length, source[0].length);

		   // Calculate the step size for the blending factor
		   double stepSize = 1.0 / n;
	   
		   for (int i = 0; i < n; i++) {
			   double alpha = i * stepSize;
	   
			   // Create a new image for the current step
			   Color[][] morphedImage = new Color[source.length][source[0].length];
	   
			   for (int y = 0; y < source.length; y++) {
				   for (int x = 0; x < source[0].length; x++) {
					   Color sourcePixel = source[y][x];
					   Color targetPixel = target[y][x];
	   
					   // Blend the pixels using the current alpha value
					   Color blendedPixel = blend(sourcePixel, targetPixel, alpha);
					   morphedImage[y][x] = blendedPixel;
				   }
			   }
	   
			   // Display the morphed image
			   display(morphedImage);
			   // Pause for a short time to visualize the transition
			   StdDraw.pause(100);
		   }
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

