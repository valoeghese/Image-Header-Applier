package valoeghese.imgheader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Simple program to apply a header image to a variety of image files.
 */
public class ImgHeader {
	public static void main(String[] args) {
		// Check correct number of arguments.
		if (args.length != 2) {
			System.err.println("Invalid number of arguments." +
					"Usage: java -jar imgheader.jar [image file regex] [header image file]");
			return;
		}

		// Try parse arguments
		try {
			Pattern imagesSelected = Pattern.compile(args[0]);
			String headerFile = args[1];

			// Load the header image.
			BufferedImage header = ImageIO.read(new File(headerFile));

			// For all images in the current directory (recursive) matching the pattern, read the images
			// and put the image header at the top of the image.
			File currentDirectory = new File(".");

			// Count and time the number of transformed images
			AtomicInteger transformedImageCount = new AtomicInteger(0);
			long startTime = System.nanoTime();

			recursivelyApply(currentDirectory, file -> {
				if (imagesSelected.matcher(file.getName()).matches()) {
					System.out.println("Transforming " + file.getName());

					try {
						BufferedImage image = ImageIO.read(file);
						BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight() + header.getHeight(), BufferedImage.TYPE_INT_ARGB);
						newImage.getGraphics().drawImage(header, 0, 0, null);
						newImage.getGraphics().drawImage(image, 0, header.getHeight(), null);
						ImageIO.write(newImage, "png", file);

						// Increment the transformed image count
						transformedImageCount.incrementAndGet();
					} catch (IOException e) {
						System.err.println("Error transforming image \"" + file.getName() + "\": " + e.getMessage());
					}
				}
			});

			// Calculate the time taken.
			double timeTaken = (System.nanoTime() - startTime) / 1_000_000_000.0;

			// Notify the user we are done. This could be especially useful when using the program
			// as part of a batch script.
			System.out.printf("Transformed %d images in %.2fs%n", transformedImageCount.get(), timeTaken);
		} catch (PatternSyntaxException e) {
			System.err.println("Invalid regex pattern \"" + args[0] + "\"");
		} catch (IOException e) {
			System.err.println("Error interacting with file system: " + e.getMessage());
		}
	}

	/**
	 * Recursively apply a consumer to all files in a directory.
	 * @param rootDirectory the root directory to search for files in.
	 * @param consumer the consumer to apply.
	 * @throws IOException if an error occurs while interacting with the file system.
	 */
	private static void recursivelyApply(File rootDirectory, IOConsumer<File> consumer) throws IOException {
		File[] files = Objects.requireNonNull(rootDirectory.listFiles(), "File is not a directory.");

		for (File file : files) {
			if (file.isDirectory()) {
				recursivelyApply(file, consumer);
			} else {
				consumer.accept(file);
			}
		}
	}
}
