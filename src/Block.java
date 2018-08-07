import java.math.BigInteger;
import java.util.*;

public class Block {

    public
    static int MAX_PF = 1000 ;
    static int MIN_PF = -1000;
    static int MAX_PARAM = 9;
    static int MIN_PARAM = 0;

    // Factors
    int hf = 0; // History Factor
    int uf = 1; // Usage Factor
    int sf = 0; // Spatial Factor
    int lf = 1; // Linking Factor

    // Used boolean
    boolean used = false;

    // Priority Score
    int pf = 0;

    // Pointer to file
    File parentFile = null ;

    // File number of this block (0 when unused)
    int filenumber = 0;

    // IO time
    int IO_time = 0;

//    Block(){
//
//    }

    void updateIOtime(){
        //depends on placement of block so  same file
        //spatial factor
    }

    // Update Priority Score
    void updatepf(int lambda, int sigma, int rho, int mu){
        //ranges of l, sig, rho , mu
        //ranges of pf check
        assert lambda <= MAX_PARAM && lambda >= MIN_PARAM ;
        assert sigma  <= MAX_PARAM && sigma  >= MIN_PARAM ;
        assert rho    <= MAX_PARAM && rho    >= MIN_PARAM ;
        assert mu     <= MAX_PARAM && mu     >= MIN_PARAM ;

        int temp = lambda*this.hf - sigma*this.uf + rho*this.sf + mu*this.lf ;
        if (temp > MAX_PF){
            this.pf = MAX_PF;
        }
        else if (temp < MIN_PF){
            this.pf = MIN_PF;
        }
        else{
            this.pf = temp;
        }
    }

//    int checkRange(int val){
//
//    }

}
