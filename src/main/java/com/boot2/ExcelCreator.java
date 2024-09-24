package com.boot2;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;

public class ExcelCreator {
    public static void main(String[] args) {
        // Sample data
        List<String> distinctItems = Arrays.asList(
                "Aurora Natural Forest Bounty Grail Mix",
                "Aveeno Stress Relief Moisturizing Body Lotion with Lavender",
                "Banana 2 types",
                "Band-Aid Assorted sizes Johnson&Johnson",
                "Bolthouse Farms Berry Boost 100% Fruit Juice Smoothie",
                "Bonne Maman Fruit Preserves Pineapple-Passion",
                "Bounty - Kitchen Towel",
                "Buttermilk - Kate's Creamery",
                "Buttermilk [Five Acre Cultured Whole Buttermilk (1 quart)]- 1",
                "Pataday eye drop",
                "Carrot Juice",
                "Carrot Juice Bolthouse",
                "Cashew salted roast",
                "Charmin Ultra Strong Toilet Paper",
                "Cranberry Trail",
                "Crest Complete Toothpaste + Scope Tartar Control Whitening Gel Minty Fresh",
                "Dasani Water Bottles [or any available]",
                "Dish Washing liquid",
                "Dixie Paper Bowl",
                "Dixie Paper Plates 10 inch big round dinner plates",
                "Dixie Paper Plates 6 1/8 Inch appetizer side plates",
                "Dixie To Go Hot Cups & Lids 12 oz 26 count pack",
                "Domino Golden Sugar Less Processed 3.5 lb pkg",
                "Duracell Coppertop Alkaline Batteries Size AAA",
                "Eggs",
                "Glad Cling Wrap Plastic Food Wrap",
                "Glad Press'n Seal Plastic Wrap [Press'n Seal]",
                "Glad Tall Kitchen Trash Bags, 13 Gallon",
                "Good Living Latex Medium Household Gloves",
                "Grail/Trail Mix, Forest's Bounty",
                "Guaranteed Value Cutlery Assorted Heavy Duty White",
                "Guaranteed Value Heavy Duty Fork Plastic White",
                "Guaranteed Value Heavy Duty Knives Plastic White",
                "Guaranteed Value Heavy Duty Spoons Plastic White",
                "Haagen-Dazs Ice Cream, Cherry Vanilla 14 fl oz",
                "Handi Works Ultrafit Synthetic Gloves",
                "Hearty White Bread",
                "Kedem tea biscuits Original",
                "Kerrygold Pure Irish Butter Salted Grass-Fed",
                "Kleenex Tissues - 3 Boxes bundle",
                "La Vieja Fabrica Premium Fruit Spread Peach",
                "Ladies' hair Jaw Clips",
                "Land O Lakes Butter, Salted 1 lb",
                "Local Hive New England Honey Raw & Unfiltered",
                "Madhava Organic Amber Honey",
                "Margie's 100% Fruit Spread Pineapple, Banana & Passion Fruit All Natural [Jam]",
                "Naked Smoothie Juice Mighty Mango No Sugar Added",
                "Oranges",
                "Peanut masala",
                "Pepperidge Farm Farmhouse Multigrain Bread",
                "Pepperidge Farm Farmhouse Oatmeal Bread - 24oz OvenTaste / Homestyle Oat",
                "Puffs Plus Lotion Facial Tissues 2-Ply White [big rectangular boxes]",
                "Q-tips Cotton Swabs [ear buds]",
                "Red label tea powder",
                "Reynolds Cut-Rite Wax Paper, Size: 75 SQ FT",
                "Smucker's Preserves peach/ Red Raspberry/ any",
                "Stop & Shop Coffee Filters Basket Style 8-12 Cup White",
                "Stop & Shop Elegant Knives Plastic Crystal Clear",
                "Stop & Shop Jumbo Cashews Roasted Salted",
                "Stop & Shop Premium Combo Cutlery Crystal Clear Plastic",
                "Tetley Classic Blend Rich Black Tea Bags"
                // Add more items here...
        );

        // Create a new workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Distinct Items");

        // Create header row
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Select");
        headerRow.createCell(1).setCellValue("Distinct Items");
        headerRow.createCell(2).setCellValue("Quantity");

        // Add data rows
        for (int i = 0; i < distinctItems.size(); i++) {
            XSSFRow row = sheet.createRow(i + 1);
            row.createCell(1).setCellValue(distinctItems.get(i));
            createDropDownList(sheet, row.getRowNum(), 2);
        }

        // Create checkboxes
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        for (int i = 1; i <= distinctItems.size(); i++) {
            CreationHelper factory = workbook.getCreationHelper();
            ClientAnchor anchor = factory.createClientAnchor();
            anchor.setCol1(0);
            anchor.setCol2(1);
            anchor.setRow1(i);
            anchor.setRow2(i + 1);
            Comment comment = drawing.createCellComment(anchor);
            comment.setString(factory.createRichTextString("Select Item"));
            sheet.getRow(i).createCell(0).setCellComment(comment);
        }

        // Adjust column widths
        sheet.setColumnWidth(0, 256 * 10);
        sheet.setColumnWidth(1, 256 * 50);
        sheet.setColumnWidth(2, 256 * 10);

        // Create the button
        XSSFRow buttonRow = sheet.createRow(distinctItems.size() + 2);
        XSSFCell buttonCell = buttonRow.createCell(1);
        buttonCell.setCellValue("Generate Printable List");

        // Save the workbook to file
        try (FileOutputStream fileOut = new FileOutputStream("distinct_items.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDropDownList(XSSFSheet sheet, int row, int col) {
        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(
                new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});
        CellRangeAddressList addressList = new CellRangeAddressList(row, row, col, col);
        DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
        dataValidation.setSuppressDropDownArrow(true);
        sheet.addValidationData(dataValidation);
    }
}
