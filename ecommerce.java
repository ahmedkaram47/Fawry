/*
  Author : Ahmed Karam
*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ecommerce 
{

HashMap<product,Integer> cart = new HashMap<product,Integer>();

void addtocart (product productname , int count)
{

    cart.put(productname,count);
}

void seecart() 
{
    if (cart.isEmpty()) 
    {
        System.out.println("Cart is empty.");
    } 
    else 
    {
        System.out.println("Items in your cart:");
        for (HashMap.Entry<product, Integer> entry : cart.entrySet()) 
            {
                product p = entry.getKey();
                int count = entry.getValue();
                System.out.println(p.name + " - Quantity: " + count + " - Price per item: " + p.price + " - Total price: " + (p.price * count));
            }
    }
}

Boolean checkout()
{
    //I will make this function return either 0 or 1 in case of failure or success respectively
    
    //Mock user balance 
    int balance = 10000;
    int ordersubtotal=0;
    int ship=0;
    HashMap<shippableproduct,Integer> toship = new HashMap<shippableproduct,Integer>();
    shippingservice DHL = new shippingservice();

    if(cart.isEmpty())
    {
        System.out.println("\n Cart is EMPTY!");
        return false;
    }
    
    for (HashMap.Entry<product, Integer> entry : cart.entrySet()) 
    {
        product p = entry.getKey();
        int count = entry.getValue();
        if (p.shipping) 
        {
            shippableproduct sp = new shippableproduct(p);
            toship.put(sp, count);
        }
    }

    ship+=DHL.shipitems(toship);

        for (HashMap.Entry<product, Integer> entry : cart.entrySet()) 
        {
            product p = entry.getKey();
            int count = entry.getValue();
            ordersubtotal+=(p.price * count);
        }


    balance -= ship;
    balance -= ordersubtotal;
    if (balance<0) 
    {
        System.out.println("You dont have enough balance to pay.");
        return false;
    }

    System.out.println("Subtotal: "+ordersubtotal);
    System.out.println("Shipping: "+ship);
    System.out.println("Amount: "+(ship+ordersubtotal));
    System.out.println("Remaining Balance: "+balance);
    
    return true;
}

interface shippable 
{
    String getname();
    int getweight();
}


class shippableproduct implements shippable 
{
    product p;

    public shippableproduct(product p) 
    {
        this.p = p;
    }

    @Override
    public String getname() 
    {
        return p.name;
    }

    @Override
    public int getweight() 
    {
        return p.weight;
    }
}

class shippingservice 
{
    public int shipitems(HashMap<shippableproduct,Integer> items) 
    {
        //i will create an imaginary shipping fee per item for example 15
        System.out.println("* Shipment Notice *");
        int totalweight=0;
        int topay=0;
        for (HashMap.Entry<shippableproduct, Integer> entry : items.entrySet()) 
        {

            shippableproduct p = entry.getKey();
            int count = entry.getValue();
            System.out.println(count+"x " + p.getname() + " with weight " + p.getweight()*count + "g");
            topay+=15;
            totalweight +=p.getweight();
        }
        System.out.printf("Total package weight %f kg \n",(float)totalweight/1000f);
        return topay;
    }
}



public static void main(String[] args)
{
    //Pre-filed list of products to act as an example to use we can also make an ArrayList and make the user fill it but since there is no test input an array will be fine.
    product cheese = new product("Cheese", 400, 10 , true , true,200);
    product biscuits = new product("Biscuits", 100, 40 , true , true,350);
    product tv = new product("TV", 4000, 4 , false , true,10000);
    product scratchcards = new product("Scratch Card", 40, 2);
    ArrayList<product> arr = new ArrayList<product>(Arrays.asList(cheese, biscuits, tv, scratchcards));


    //int custbalance = 10000 ; //mock balance for customer --- I am going to make it similar to a payment process instead and make the mock balance in the checkout func
    
    ecommerce fawry = new ecommerce();

    System.out.println("\nWelcome to our store!");
    while (true) 
    {
        Scanner s = new Scanner(System.in);

        System.out.println("Please choose an operation:");
        System.out.println("1 - Add Product to Cart");
        System.out.println("2 - Checkout");
        System.out.println("3 - See Cart");
        System.out.println("4 - Exit");
        System.out.print("Your choice: ");
        
        int choice = s.nextInt();
        s.nextLine(); //It is a good practise i do to empty the input buffer after the next int even if i am going to close the buffer rn
        //s.close();

        switch (choice) 
        {
            case 1:
                System.out.println("please enter product name and count.");
                String input = s.nextLine();
                int count = s.nextInt();
                s.nextLine();
                System.out.println(input + " " + count + "\n");
                for (product p : arr) 
                {
                    if (p.name.equalsIgnoreCase(input))
                    {
                        if (p.quantity_instock >= count) 
                        {
                            p.quantity_instock = p.quantity_instock - count;
                            fawry.addtocart(p, count);
                            System.out.printf("Added %s to Cart\n",p.name);
                            break;
                        }
                        else 
                        {System.out.println("Not Enough in stock!"); break;}

                    }
                    //System.err.println("Operation Failed.");
                }

                
                //fawry.addtocart(,);
                break;
            case 2:
                if(fawry.checkout()) System.out.println("Checkout Succeeded!");
                else System.out.println("Checkout Failed");
                break;
            
            case 3:
                fawry.seecart();
                //fawry showing cart
                break;
                
            case 4:
                System.out.println("Thank you for visiting Goodbye!");
                s.close();
                return;
                
            
            default:
                System.out.println("Invalid choice please try again.");
                break;
        }

    }
    
    
}

}