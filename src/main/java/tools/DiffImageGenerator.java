package tools;

import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.comparison.PointsMarkupPolicy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DiffImageGenerator
{

    public int getDiffSize(Screenshot expected, Screenshot actual)
    {
        ImageDiff diff = new ImageDiffer().makeDiff(expected, actual);
        return diff.getDiffSize();
    }

    public void saveDiffImage(Screenshot expected, Screenshot actual, String fileName)
    {
        ImageDiffer imageDiffer = new ImageDiffer().withDiffMarkupPolicy(new PointsMarkupPolicy().withDiffColor(Color.RED));
        ImageDiff diff = imageDiffer.makeDiff(expected, actual);

        File diffFile = new File(TestConfig.PATH_TO_DIFF_SCREENSHOTS + fileName + ".png");

        try {
            ImageIO.write(diff.getMarkedImage(), "png", diffFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
