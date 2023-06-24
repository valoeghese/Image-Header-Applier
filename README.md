# Image Header Applier

Applies a header image to a set of images. The width of the header must be the width of the set of images.
The "header" image is placed at the top of each image. For best results, the width of the header should be the
same as the width of the images it is being applied to.

Originally designed for creating animated memes with a constant header, but this can be used for any other purpose.

Usage: `java -jar imgheader.jar [image file regex] [header image]`.

For example: `java -jar imgheader.jar \d+\.jpg header.jpg`.

The output files will be prefixed with `output_`.