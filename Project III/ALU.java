public class ALU {
	public int ALUoperation( int data1, int data2, int select ) {
		int result = 0;
		switch(select) {
			case 0:
				result = data1 + data2;		//Addition
				break;
			case 1:
				result = data1 - data2;		//Subtraction
				break;
			case 2:
				result = data1 * data2;		//Multiplication
				break;
			case 3:
				result = data1 / data2;		//Integer division
				break;
			case 4:
				result = data1 & data2;		//Bitwise AND
				break;
			case 5:
				result = data1 | data2;		//Bitwise OR
				break;
			case 6:
				result = ~( data1|data2 );	//Bitwise NOR
				break;
			case 7:
				result = data1 ^ data2;		//Bitwise XOR
				break;
			case 8:
				result = data1 >> data2;	//Shift right
				break;
			case 9:
				result = data1 << data2;	//Shift left
				break;
			case 10:
				result = ( data1 < data2 )? 1:0;	//Set if (Data1 < Data2)
				break;
			case 11:
				System.out.println("syscall");		//Syscall
				break;
			case 12:
				result = (data1==data2)? 1:0;		//Set if (Data1 = Data2)
				break;
			case 13:
				result = (data1>data2)? 1:0;		//Set if (Data1 > Data2)
				break;
			default :
				System.out.println("ALU Operation failed");	
		}
		return result;
	}
}
