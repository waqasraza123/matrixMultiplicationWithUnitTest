package lab1_matrix_multiplication;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class MatrixTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testMultiply_Old() {
		int[][] expected = {{16,19},{36,43}};
		
		int[][] aTemp = {{1,2},{3,4}};
		Matrix A = new Matrix(aTemp);
		
		int[][] bTemp = {{4,5},{6,7}};
		Matrix B = new Matrix(bTemp);
		
		Assert.assertArrayEquals(expected,A.multiply_Old(B).dataContainer);
	}

	@Test
	public final void testMultiply_Strassen() {
int[][] expected = {{16,19},{36,43}};
		
		int[][] aTemp = {{1,2},{3,4}};
		Matrix A = new Matrix(aTemp);
		
		int[][] bTemp = {{4,5},{6,7}};
		Matrix B = new Matrix(bTemp);
		
		Matrix C = new Matrix(2,2);
		Assert.assertArrayEquals(expected,C.multiply_Strassen(A.dataContainer,B.dataContainer).dataContainer);
	}

}
