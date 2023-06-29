package edu.uoc.pac1;

public class PAC1Ex2 {

    public static double twoDecimals(double number){
        return Math.round(number * 100d) / 100d;
    }

    public static int incomeTaxRate(double amount){

        // Constant & var
        int rate;

        // Get rate function of amount
        if (amount<=0){ // error case
            rate=-1;
        } else if (amount>0 && amount<=15000){
            rate=15;
        } else if (amount>15000 && amount<=24800){
            rate=18;
        } else {
            rate=22;
        }

        return rate;
    }

    public static double totalIncomeTaxVAT(double amount) {

        // Constant & var
        int rate;
        final byte IVA=21;

        // Check input data validity
        if (amount<=0){
            return twoDecimals(0);

        // Perform calculations
        } else {
            rate=incomeTaxRate(amount);
            return twoDecimals(amount - (amount * rate)/100 + (amount * IVA)/100);
        }
    }

    public static double[] invoicesTotal(double[][] invoices) {

        // Array initialization
        double[] result=new double[invoices.length];

        // Iterate over customers
        for (int row=0; row<invoices.length; ++row){

            // Variables
            double sum=0;

            // Iterate over invoices
            for (int col=0; col<invoices[row].length; ++col){
                sum+=invoices[row][col];
            }

            int client_num=row+1;

            // Update array & create output
            result[row]=totalIncomeTaxVAT(sum);
            System.out.print("Client " + client_num + ": " + result[row] + System.lineSeparator());
        }
        return result;
    }
}
