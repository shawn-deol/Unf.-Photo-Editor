package PhotoEditor;

import becker.xtras.imageTransformation.ITransformations;
import hdsb.rbhs.ICS3U.deol.u4.arrayUtils;

public class Transformer extends Object implements ITransformations {

    public static final int MIN_NUM_TRANS = 10;
    public static final String DARKEN = "Darken";
    public static final String BRIGHTEN = "Brighten";
    public static final String INVERT = "Invert";
    public static final String FLIPX = "Flip X";
    public static final String FLIPY = "Flip Y";
    public static final String ROTATE = "Rotate";
    public static final String SCALE50 = "Scale 50%";
    public static final String MIRROR = "Mirror";
    public static final String BLUR = "Blur";
    public static final String RESET = "Reset";
    private int numTrans = 10;
    private String[] transformations = new String[MIN_NUM_TRANS];
    private static final int MAX_INTENSITY = 255;   // the value for pure white
    private static final int MIN_INTENSITY = 0;     // the value for pure black
    private static final int INTENSITY_STEP = 10;   // the value used to increase or decrease the brightness
    private int[][] pictureOriginal;
    private int[][] picture;

    /** Construct a Transformer object by setting the possible transformations available. 
     */
    public Transformer() {
        super();
        this.transformations[0] = DARKEN;
        this.transformations[1] = BRIGHTEN;
        this.transformations[2] = INVERT;
        this.transformations[3] = FLIPX;
        this.transformations[4] = FLIPY;
        this.transformations[5] = ROTATE;
        this.transformations[6] = SCALE50;
        this.transformations[7] = MIRROR;
        this.transformations[8] = BLUR;
        this.transformations[9] = RESET;
    }

    /** Construct a Transformer object by setting the possible transformations available and initializing the
    state of the image.
    @param pictureOrigional A 2-D array representing a grey scale image. The array contains values from 0 - 255.
     */
    public Transformer(int[][] originalPic) {
        this();
        this.setPixels(originalPic);
    }

    /** Get the image that was transformed.
     * @return The pixels representing the image. */
    public int[][] getPixels() {
        return this.picture;
    }

    /** Set the image to be transformed to a new set of pixels.
     * @param newPix The new image to be used for subsequent transformations. */
    public void setPixels(int[][] newPix) {
        this.pictureOriginal = newPix;
        this.picture = this.copyArray(newPix);
    }

    /** A array filled with the names of the transformations implemented by this
     * class.
     * @return The array of transformation names. */
    public String[] getTransformationNames() {
        return transformations;
    }

    public static void display(int[][] twoDArray) {
        for (int row = 0; row < twoDArray.length; row++) {
            for (int col = 0; col < twoDArray[row].length; col++) {
                if (twoDArray[row][col] == 0) {
                    System.out.print(" .");
                } else {
                    System.out.print(" O");
                }
            }
            System.out.println("");
        }
    }

    /** Perform the transformation indicated.
     * @param transformationName The name of the transformation to perform.  Must
     * be one of the transformation names returned by {@link #getTransformationNames getTransformationNames}. */
    public void performTransformation(String transformationName) {

        if (DARKEN.equals(transformationName)) {
            this.picture = changeIntensity(-1, this.picture);
        } else if (BRIGHTEN.equals(transformationName)) {
            this.picture = changeIntensity(1, this.picture);
        } else if (INVERT.equals(transformationName)) {
            this.picture = invert(this.picture);
        } else if (FLIPX.equals(transformationName)) {
            this.picture = flipX(this.picture);
        } else if (FLIPY.equals(transformationName)) {
            this.picture = flipY(this.picture);
        } else if (ROTATE.equals(transformationName)) {
            this.picture = rotate(this.picture);
        } else if (MIRROR.equals(transformationName)) {
            this.picture = mirror(this.picture);
        } else if (SCALE50.equals(transformationName)) {
            this.picture = scale50(this.picture);
        } else if (BLUR.equals(transformationName)) {
            this.picture = blur(this.picture);
        } else if (RESET.equals(transformationName)) {
            this.picture = this.copyArray(this.pictureOriginal);
        } else {
            throw new Error("Invalid transformation requested.");
        }
    }

    private int[][] copyArray(int[][] a) {
        //Create new array
        int[][] b = new int[a.length][a[0].length];
        //For loop to cycle rows
        for (int row = 0; row < a.length; row++) {
            //For loop to cycle columns
            for (int col = 0; col < a[row].length; col++) {
                //Set new array
                b[row][col] = a[row][col];
            }
        }
        return b;
    }
//Method to lighten/darken image

    private int[][] changeIntensity(int percent, int[][] sourcePixels) {
        //For loop to cycle rows
        for (int row = 0; row < sourcePixels.length; row++) {
            //For loop to cycle columns
            for (int col = 0; col < sourcePixels[row].length; col++) {
                //add 10/-10 to the pixel and lighten it/darken it
                sourcePixels[row][col] = (INTENSITY_STEP * percent) + sourcePixels[row][col];
                //If the program tries to add 10 to the source pixel and it goes over the max intensity, the pixel is set to the max intensity
                if (sourcePixels[row][col] >= MAX_INTENSITY) {
                    sourcePixels[row][col] = MAX_INTENSITY;
                } //Same as above but with min intensity
                else if (sourcePixels[row][col] <= 0) {
                    sourcePixels[row][col] = MIN_INTENSITY;
                }

            }
        }
        return sourcePixels;
    }
//Method to invert the colours in an image

    private int[][] invert(int[][] sourcePixels) {
        //For loop that cycles rows
        for (int row = 0; row < sourcePixels.length; row++) {
            //Cycles columns
            for (int col = 0; col < sourcePixels[row].length; col++) {
                //Set to the pixel so its inverse counterpart
                sourcePixels[row][col] = 255 - sourcePixels[row][col];
            }
        }
        return sourcePixels;
    }
    //Method to flip the image on its x-axis

    private int[][] flipX(int[][] sourcePixels) {
        //Intiate new array with same paramters as source array
        int[][] flippedXArray = new int[sourcePixels.length][sourcePixels[0].length];
        //Cycle rows
        for (int row = 0; row < sourcePixels.length; row++) {
            //Cycle columns
            for (int col = 0; col < sourcePixels[row].length; col++) {
                //Set the flipped array to the source array but work backwards with the rows while keeping the columns in the same order
                flippedXArray[row][col] = sourcePixels[sourcePixels.length - 1 - row][col];
            }
        }
        return flippedXArray;
    }
//Method to flip the image on its x-axis

    private int[][] flipY(int[][] sourcePixels) {
        int flippedYArray[];
        //For loop to cycle through rows
        for (int rowLength = 0; rowLength < sourcePixels.length; rowLength++) {
            //Set the new array to the present row's length
            flippedYArray = new int[sourcePixels[rowLength].length];
            //For loop to set the first value of the new array to the last value of the input array
            for (int columnLength = 0; columnLength < sourcePixels[rowLength].length; columnLength++) {
                flippedYArray[columnLength] = sourcePixels[rowLength][sourcePixels[rowLength].length - 1 - columnLength];
            }
            //Set all the info from the reversed array into the original array
            for (int column = 0; column < sourcePixels[rowLength].length; column++) {
                sourcePixels[rowLength][column] = flippedYArray[column];
            }
        }
        return sourcePixels;
    }
//Method to rotate an array 90 degrees

    private int[][] rotate(int[][] sourcePixels) {
        //Intiate new array with the row length set to sourcePixel's column length and and column length set to sourcePixel's row length
        int[][] rotatedArray = new int[sourcePixels[0].length][sourcePixels.length];
        //Cycle rows
        for (int row = 0; row < sourcePixels[0].length; row++) {
            //Cycle columns
            for (int col = 0; col < sourcePixels.length; col++) {
                //Set the rotated pixel to the source Pixels end column of the first row
                rotatedArray[row][col] = sourcePixels[sourcePixels.length - 1 - col][row];
            }
        }
        return rotatedArray;
    }
//Method to create a identical mirrored image of the source picture, flipped on the y-axis

    private int[][] mirror(int[][] sourcePixels) {
        //Initiate new array with column length double that of the source arrays
        int[][] mirroredArray = new int[sourcePixels.length][sourcePixels[0].length * 2];
        //Cycle rows
        for (int row = 0; row < sourcePixels.length; row++) {
            //Cycle columns
            for (int col = 0; col < sourcePixels[row].length; col++) {
                //Setting array front and back at the same time saves time
                //Set mirrored array to be identical to the source array (left side)
                mirroredArray[row][col] = sourcePixels[row][col];
                //Set mirrored array to be flipped on the y axis by working backwards (right side)
                mirroredArray[row][mirroredArray[row].length - col - 1] = sourcePixels[row][col];
            }
        }
        return mirroredArray;
    }
//Method to scale array to half of its length and half of its width

    private int[][] scale50(int[][] sourcePixels) {
        //Initiate new array with half the width and length of the original
        int[][] scaledArray = new int[sourcePixels.length / 2][sourcePixels[0].length / 2];
        //Cycle rows (half)
        for (int row = 0; row < sourcePixels.length / 2; row++) {
            //Cycle columns (half)
            for (int col = 0; col < sourcePixels[row].length / 2; col++) {
                //Set the scaled array pixel to the average of 4 pixels since the scaled array is 1/4th of the original array size
                //Multiply the rows and columns so that all pixels (groups of 4)are scaled
                scaledArray[row][col] = (sourcePixels[row * 2][col * 2] + sourcePixels[row * 2][col * 2 + 1] + sourcePixels[row * 2 + 1][col * 2] + sourcePixels[row * 2 + 1][col * 2 + 1]) / 4;
            }
        }
        return scaledArray;
    }
//Method to blur array by making it similar to the 3, 5 or 8 sorrounding it

    private int[][] blur(int[][] sourcePixels) {
        //Initiate new array with the same size as the source
        int[][] BlurredArray = new int[sourcePixels.length][sourcePixels[0].length];
        //Cycle rows
        for (int row = 0; row < sourcePixels.length; row++) {
            //Cycle columns
            for (int col = 0; col < sourcePixels[row].length; col++) {
                //Large nested IF statement to check all 3 top possibilities, either top left corner, top right corner or anywhere else on the top row
                //ALL TOP POSSIBILITIES
                if (row == 0) {
                    //IF NOT TOP LEFT CORNER
                    if (col > 0) {
                        //If statement to determine if pixel is top right corner
                        //IF TOP RIGHT CORNER
                        if (col == sourcePixels[row].length - 1) {
                            //Blur pixel by averaging the pixel and those around it..
                            BlurredArray[row][col] = ((sourcePixels[row][col] + sourcePixels[row][col - 1] + sourcePixels[row + 1][col] + sourcePixels[row + 1][col - 1])) / 4;
                        } //Since the pixel is not the top right corner it is assumed that it's the top side
                        //TOP SIDE
                        else {
                            //Blur pixel by averaging the pixel and those around it..
                            BlurredArray[row][col] = ((sourcePixels[row][col] + sourcePixels[row][col + 1] + sourcePixels[row][col - 1] + sourcePixels[row + 1][col - 1] + sourcePixels[row + 1][col] + sourcePixels[row + 1][col + 1])) / 6;
                        }
                    } //TOP LEFT CORNER
                    //Since col !=0 it is assumed that the pixel is the top left corner
                    else {
                        //Blur pixel by averaging the pixel and those around it..
                        BlurredArray[row][col] = ((sourcePixels[row][col] + sourcePixels[row][col + 1] + sourcePixels[row + 1][col] + sourcePixels[row + 1][col + 1])) / 4;
                    }
                } //Second large nested if statement to check all bottom row possibilities
                //ALL BOTTOM POSSIBILITIES
                else if (row == sourcePixels.length - 1) {

                    //IF NOT TOP LEFT CORNER
                    if (col > 0) {
                        //If statement to determine if pixel is bottom right corner
                        //IF BOTTOM RIGHT CORNER
                        if (col == sourcePixels[row].length - 1) {
                            //Blur pixel by averaging the pixel and those around it..
                            BlurredArray[row][col] = ((sourcePixels[row][col] + sourcePixels[row][col - 1] + sourcePixels[row - 1][col] + sourcePixels[row - 1][col - 1])) / 4;
                        } //Since the pixel is not the bottom right corner it is assumed that it's the bottom row
                        //BOTTOM SIDE
                        else {
                            //Blur pixel by averaging the pixel and those around it..
                            BlurredArray[row][col] = ((sourcePixels[row][col] + sourcePixels[row][col - 1] + sourcePixels[row][col + 1] + sourcePixels[row - 1][col] + sourcePixels[row - 1][col + 1] + sourcePixels[row][col - 1]) / 6);
                        }
                    } //BOTTOM LEFT CORNER
                    //Since col !=0 it is assumed that the pixel is the bottom left corner
                    else {
                        //Blur pixel by averaging the pixel and those around it..
                        BlurredArray[row][col] = ((sourcePixels[row][col] + sourcePixels[row][col + 1] + sourcePixels[row - 1][col] + sourcePixels[row - 1][col + 1]) / 4);
                    }
                } //If statement for the left  side of the image
                //RIGHT POSSIBILITIES
                else if (col == 0) {
                    //Blur pixel by averaging the pixel and those around it..
                    BlurredArray[row][col] = ((sourcePixels[row][col] + sourcePixels[row][col + 1] + sourcePixels[row - 1][col] + sourcePixels[row - 1][col + 1] + sourcePixels[row + 1][col] + sourcePixels[row + 1][col + 1]) / 6);
                } //If statement for the right side of the image
                //LEFT POSSIBILITIES
                else if (col == sourcePixels[row].length - 1) {
                    //Blur pixel by averaging the pixel and those around it..
                    BlurredArray[row][col] = ((sourcePixels[row][col] + sourcePixels[row][col - 1] + sourcePixels[row - 1][col] + sourcePixels[row - 1][col - 1] + sourcePixels[row + 1][col] + sourcePixels[row + 1][col - 1]) / 6);
                } //UNBORDERED POSSIBILITIES
                //Assuming that the pixel being set is not on any of the border spaces the pixel must be sorrounded on all sides thus the below equation
                else {
                    BlurredArray[row][col] = ((sourcePixels[row][col] + sourcePixels[row][col - 1] + sourcePixels[row][col + 1] + sourcePixels[row - 1][col - 1] + sourcePixels[row - 1][col] + sourcePixels[row - 1][col + 1] + sourcePixels[row + 1][col - 1] + sourcePixels[row + 1][col]) + sourcePixels[row - 1][col + 1]) / 9;
                }
                {
                }
            }
        }
        return BlurredArray;
    }

    static void main(String[] args) {

        int[][] myPicture = new int[4][15];

        myPicture[

0][0] = 1;
        myPicture[

1][1] = 1;
        myPicture[

2][2] = 1;
        myPicture[

3][3] = 1;
        myPicture[

2][4] = 1;
        myPicture[

1][5] = 1;
        myPicture[

2][6] = 1;
        myPicture[

3][7] = 1;
        myPicture[

2][8] = 1;
        myPicture[

1][9] = 1;
        myPicture[

0][10] = 1;


//       Construct the test object
        Transformer test = new Transformer(myPicture);

//       Display Original Image
        System.out.println("Original\n");
        display(
                myPicture);

//       Test flip on X-axis
        System.out.println("\nFlipped on the X axis.\n");
        test.performTransformation(FLIPX);
        display(
                test.getPixels());

//       Test flip on Y-axis
        System.out.println("\nFlipped on the Y axis.\n");
        test.performTransformation(FLIPY);
        display(
                test.getPixels());

//       Test Rotate 90 degrees
        System.out.println("\nRotated 90 degrees.\n");
        test.performTransformation(ROTATE);
        display(
                test.getPixels());

//       Test Rotate Scale 50%
        System.out.println("\nScaled 50%.\n");
        test.performTransformation(SCALE50);
        display(
                test.getPixels());

//       Test Mirror Image
        System.out.println("\nMirror image.\n");
        test.performTransformation(MIRROR);
        display(
                test.getPixels());

//       Test Reset
        System.out.println("\nReset image.\n");
        test.performTransformation(RESET);
        display(
                test.getPixels());



    }
}
