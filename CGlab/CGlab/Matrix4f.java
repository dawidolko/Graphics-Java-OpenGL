package CGlab;

public class Matrix4f {

    public enum Matrix {
 	Zeros, Ones, Identity;
    }

    private float[][] data;

    public Matrix4f(){
        data = new float[4][4];
	    for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
	            this.data[i][j] = 0;
	        }
 	    }
    }

    public Matrix4f(float m00, float m01, float m02, float m03,
            float m10, float m11, float m12, float m13,
            float m20, float m21, float m22, float m23,
            float m30, float m31, float m32, float m33)
    {
        data = new float[4][4];
        this.data[0][0] = m00;
        this.data[0][1] = m01;
        this.data[0][2] = m02;
        this.data[0][3] = m03;

        this.data[1][0] = m10;
        this.data[1][1] = m11;
        this.data[1][2] = m12;
        this.data[1][3] = m13;

        this.data[2][0] = m20;
        this.data[2][1] = m21;
        this.data[2][2] = m22;
        this.data[2][3] = m23;

        this.data[3][0] = m30;
        this.data[3][1] = m31;
        this.data[3][2] = m32;
        this.data[3][3] = m33;
    }

    public float data(int row, int col) {
      return data[row][col];
    }


    public Matrix4f(Matrix matrixType){
      data = new float[4][4];
    	if(matrixType == Matrix.Zeros){
        	for(int i = 0; i < 4; i++) {
        		for(int j = 0; j < 4; j++) {
        			 this.data[i][j] = 0;
        		}
        	}
        }
        if(matrixType == Matrix.Ones){
            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 4; j++) {
                     this.data[i][j] = 1;
                }
            }
        }
        if(matrixType == Matrix.Identity){
            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 4; j++) {
                     if(i == j) this.data[i][j] = 1;
                     else this.data[i][j] = 0;
                }
            }
        }
    }

    public static Vec4f multiply(Matrix4f m, Vec4f v){
        float v00, v01, v02, v03;
        v00 = m.data(0, 0)*v.x + m.data(0, 1)*v.y + 
              m.data(0, 2)*v.z + m.data(0, 3)*v.w;  
        v01 = m.data(1, 0)*v.x + m.data(1, 1)*v.y + 
              m.data(1, 2)*v.z + m.data(1, 3)*v.w;  
        v02 = m.data(2, 0)*v.x + m.data(2, 1)*v.y + 
              m.data(2, 2)*v.z + m.data(2, 3)*v.w;  
        v03 = m.data(3, 0)*v.x + m.data(3, 1)*v.y + 
              m.data(3, 2)*v.z + m.data(3, 3)*v.w;        
        return new Vec4f(v00, v01, v02, v03);   
    }

    public void multiply(Matrix4f m1){
        float  m00, m01, m02, m03,
               m10, m11, m12, m13,
               m20, m21, m22, m23,
               m30, m31, m32, m33; 

        m00 = this.data[0][0]*m1.data[0][0] + this.data[0][1]*m1.data[1][0] +
              this.data[0][2]*m1.data[2][0] + this.data[0][3]*m1.data[3][0];
        m01 = this.data[0][0]*m1.data[0][1] + this.data[0][1]*m1.data[1][1] +
              this.data[0][2]*m1.data[2][1] + this.data[0][3]*m1.data[3][1];
        m02 = this.data[0][0]*m1.data[0][2] + this.data[0][1]*m1.data[1][2] +
              this.data[0][2]*m1.data[2][2] + this.data[0][3]*m1.data[3][2];
        m03 = this.data[0][0]*m1.data[0][3] + this.data[0][1]*m1.data[1][3] +
              this.data[0][2]*m1.data[2][3] + this.data[0][3]*m1.data[3][3];

        m10 = this.data[1][0]*m1.data[0][0] + this.data[1][1]*m1.data[1][0] +
              this.data[1][2]*m1.data[2][0] + this.data[1][3]*m1.data[3][0];
        m11 = this.data[1][0]*m1.data[0][1] + this.data[1][1]*m1.data[1][1] +
              this.data[1][2]*m1.data[2][1] + this.data[1][3]*m1.data[3][1];
        m12 = this.data[1][0]*m1.data[0][2] + this.data[1][1]*m1.data[1][2] +
              this.data[1][2]*m1.data[2][2] + this.data[1][3]*m1.data[3][2];
        m13 = this.data[1][0]*m1.data[0][3] + this.data[1][1]*m1.data[1][3] +
              this.data[1][2]*m1.data[2][3] + this.data[1][3]*m1.data[3][3];

        m20 = this.data[2][0]*m1.data[0][0] + this.data[2][1]*m1.data[1][0] +
              this.data[2][2]*m1.data[2][0] + this.data[2][3]*m1.data[3][0];
        m21 = this.data[2][0]*m1.data[0][1] + this.data[2][1]*m1.data[1][1] +
              this.data[2][2]*m1.data[2][1] + this.data[2][3]*m1.data[3][1];
        m22 = this.data[2][0]*m1.data[0][2] + this.data[2][1]*m1.data[1][2] +
              this.data[2][2]*m1.data[2][2] + this.data[2][3]*m1.data[3][2];
        m23 = this.data[2][0]*m1.data[0][3] + this.data[2][1]*m1.data[1][3] +
              this.data[2][2]*m1.data[2][3] + this.data[2][3]*m1.data[3][3];

        m30 = this.data[3][0]*m1.data[0][0] + this.data[3][1]*m1.data[1][0] +
              this.data[3][2]*m1.data[2][0] + this.data[3][3]*m1.data[3][0];
        m31 = this.data[3][0]*m1.data[0][1] + this.data[3][1]*m1.data[1][1] +
              this.data[3][2]*m1.data[2][1] + this.data[3][3]*m1.data[3][1];
        m32 = this.data[3][0]*m1.data[0][2] + this.data[3][1]*m1.data[1][2] +
              this.data[3][2]*m1.data[2][2] + this.data[3][3]*m1.data[3][2];
        m33 = this.data[3][0]*m1.data[0][3] + this.data[3][1]*m1.data[1][3] +
              this.data[3][2]*m1.data[2][3] + this.data[3][3]*m1.data[3][3];

        this.data[0][0] = m00; this.data[0][1] = m01; this.data[0][2] = m02; this.data[0][3] = m03;
        this.data[1][0] = m10; this.data[1][1] = m11; this.data[1][2] = m12; this.data[1][3] = m13;
        this.data[2][0] = m20; this.data[2][1] = m21; this.data[2][2] = m22; this.data[2][3] = m23;
        this.data[3][0] = m30; this.data[3][1] = m31; this.data[3][2] = m32; this.data[3][3] = m33;
    }

    public static Matrix4f multiply(Matrix4f m0, Matrix4f m1){
      float  m00, m01, m02, m03,
             m10, m11, m12, m13,
             m20, m21, m22, m23,
             m30, m31, m32, m33; 

      m00 = m0.data[0][0]*m1.data[0][0] + m0.data[0][1]*m1.data[1][0] +
            m0.data[0][2]*m1.data[2][0] + m0.data[0][3]*m1.data[3][0];
      m01 = m0.data[0][0]*m1.data[0][1] + m0.data[0][1]*m1.data[1][1] +
            m0.data[0][2]*m1.data[2][1] + m0.data[0][3]*m1.data[3][1];
      m02 = m0.data[0][0]*m1.data[0][2] + m0.data[0][1]*m1.data[1][2] +
            m0.data[0][2]*m1.data[2][2] + m0.data[0][3]*m1.data[3][2];
      m03 = m0.data[0][0]*m1.data[0][3] + m0.data[0][1]*m1.data[1][3] +
            m0.data[0][2]*m1.data[2][3] + m0.data[0][3]*m1.data[3][3];

      m10 = m0.data[1][0]*m1.data[0][0] + m0.data[1][1]*m1.data[1][0] +
            m0.data[1][2]*m1.data[2][0] + m0.data[1][3]*m1.data[3][0];
      m11 = m0.data[1][0]*m1.data[0][1] + m0.data[1][1]*m1.data[1][1] +
            m0.data[1][2]*m1.data[2][1] + m0.data[1][3]*m1.data[3][1];
      m12 = m0.data[1][0]*m1.data[0][2] + m0.data[1][1]*m1.data[1][2] +
            m0.data[1][2]*m1.data[2][2] + m0.data[1][3]*m1.data[3][2];
      m13 = m0.data[1][0]*m1.data[0][3] + m0.data[1][1]*m1.data[1][3] +
            m0.data[1][2]*m1.data[2][3] + m0.data[1][3]*m1.data[3][3];

      m20 = m0.data[2][0]*m1.data[0][0] + m0.data[2][1]*m1.data[1][0] +
            m0.data[2][2]*m1.data[2][0] + m0.data[2][3]*m1.data[3][0];
      m21 = m0.data[2][0]*m1.data[0][1] + m0.data[2][1]*m1.data[1][1] +
            m0.data[2][2]*m1.data[2][1] + m0.data[2][3]*m1.data[3][1];
      m22 = m0.data[2][0]*m1.data[0][2] + m0.data[2][1]*m1.data[1][2] +
            m0.data[2][2]*m1.data[2][2] + m0.data[2][3]*m1.data[3][2];
      m23 = m0.data[2][0]*m1.data[0][3] + m0.data[2][1]*m1.data[1][3] +
            m0.data[2][2]*m1.data[2][3] + m0.data[2][3]*m1.data[3][3];

      m30 = m0.data[3][0]*m1.data[0][0] + m0.data[3][1]*m1.data[1][0] +
            m0.data[3][2]*m1.data[2][0] + m0.data[3][3]*m1.data[3][0];
      m31 = m0.data[3][0]*m1.data[0][1] + m0.data[3][1]*m1.data[1][1] +
            m0.data[3][2]*m1.data[2][1] + m0.data[3][3]*m1.data[3][1];
      m32 = m0.data[3][0]*m1.data[0][2] + m0.data[3][1]*m1.data[1][2] +
            m0.data[3][2]*m1.data[2][2] + m0.data[3][3]*m1.data[3][2];
      m33 = m0.data[3][0]*m1.data[0][3] + m0.data[3][1]*m1.data[1][3] +
            m0.data[3][2]*m1.data[2][3] + m0.data[3][3]*m1.data[3][3];

      return new Matrix4f(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
  }

  @Override
  public String toString() {
      return this.data[0][0] + " " + this.data[0][1] + " " + this.data[0][2] + " " + this.data[0][3] + "\n" + 
             this.data[1][0] + " " + this.data[1][1] + " " + this.data[1][2] + " " + this.data[1][3] + "\n" + 
             this.data[2][0] + " " + this.data[2][1] + " " + this.data[2][2] + " " + this.data[2][3] + "\n" + 
             this.data[3][0] + " " + this.data[3][1] + " " + this.data[3][2] + " " + this.data[3][3];
                 
  }

}
