import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static PDDocument pdDocument = null;
    private static String destinationFolder = "D:\\Work\\PDF_POC\\public\\savePdf";

    public static void main(String[] args) throws IOException {
        boolean quit = false;
        int choice = 0;

        System.out.println("Please Enter\n" +
                "0 - Exit\n" +
                "1 - Split\n" +
                "2 - Merge");

        while (!quit) {
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    quit = true;
                    break;

                case 1:
                    splitPDFFile();
                    break;

                case 2:
                    mergePDFFiles();
                    break;
            }
        }
    }

    private static void mergePDFFiles() throws IOException {
        int choice = 0;
        boolean quit = false;
        List<String> listFileNames = new ArrayList<String>();
        PDFMergerUtility mergerUtility = new PDFMergerUtility();

        System.out.println("Please Enter\n" +
                "0 - Exit\n" +
                "1 - Add fileName");

        while (!quit){
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 0:
                    quit = true;
                    break;

                case 1:
                    System.out.println("Please enter the absolute file path - ");
                    String absoluteFilePath = scanner.nextLine();
                    listFileNames.add(absoluteFilePath);
                    break;
            }
        }

        mergerUtility.setDestinationFileName(destinationFolder + File.separator + "Merged_PDF_File.pdf");

        for (String strFileName:listFileNames){
            mergerUtility.addSource(new File(strFileName));
        }

        mergerUtility.mergeDocuments();
        System.out.println("Documents Merged");
    }

    private static PDDocument splitPDFFile() throws IOException {
        PDDocument documentFinal = new PDDocument();

        System.out.println("Enter the absolute file path to split - ");
        String sourceFilePath = scanner.nextLine();
        File sourceFile = new File(sourceFilePath);
        pdDocument = PDDocument.load(sourceFile);

        System.out.println(sourceFile.getName() + " is of " + pdDocument.getNumberOfPages() + "pages.");
        System.out.println("Enter fromPageNumber - ");
        int fromPageNumber = scanner.nextInt();
        System.out.println("Enter toPageNumber - ");
        int toPageNumber = scanner.nextInt();

        if (fromPageNumber>=1 && (toPageNumber>=1 || toPageNumber<=pdDocument.getNumberOfPages())){
            for (int i = fromPageNumber - 1; i < toPageNumber; i++) {
                PDPage page = pdDocument.getPage(i);
                documentFinal.addPage(page);
            }

            String strFinal = destinationFolder + File.separator + fromPageNumber + "-" + toPageNumber + "_" + sourceFile.getName();
            documentFinal.save(strFinal);
            System.out.println("File successfully created. Path - " + strFinal);
            documentFinal.close();

            return documentFinal;
        }else {
            System.out.println("Please enter fromPageNumber and toPageNumber from the specified range.");
            return null;
        }
    }
}
