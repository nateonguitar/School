using UnityEngine;

public class PlayerMovement : MonoBehaviour
{
    public float speed = 6f;
    float originalSpeed;
    float poweredUpSpeed;
    public float poweredUpTimer;

    Vector3 movement;
    Animator anim;
    Rigidbody playerRigidbody;
    int floorMask;
    float camRayLength = 100;
    public bool speedPoweredUp = false;
    public bool launched = false;
    public bool launching = false;
    float timeSinceLaunched = 0;
    public Vector3 jumpPathStartingDirection;
    public Vector3 jumpStartingPosition;
    public float launchSpeed = 1;
    bool playerFellOff = false;

    void Awake()
    {
        originalSpeed = speed;
        poweredUpSpeed = speed * 3;
        floorMask = LayerMask.GetMask("Floor");
        anim = GetComponent<Animator>();
        playerRigidbody = GetComponent<Rigidbody>();
    }

    void Update()
    {
       
    }

    void FixedUpdate()
    {
        float h = Input.GetAxisRaw("Horizontal");
        float v = Input.GetAxisRaw("Vertical"); 
        

        if (speedPoweredUp)
        {
            speed = poweredUpSpeed;
            poweredUpTimer -= Time.deltaTime;
            if(poweredUpTimer <= 0)
            {
                speedPoweredUp = false;
            }
        }
        else
        {
            speed = originalSpeed;
        }


        // if you fall off of the world
        if (playerRigidbody.position.y < -2)
        {
            playerFellOff = true;
            playerRigidbody.MovePosition(new Vector3(0, 0, 0));
        }

        if (!launched)
        {
            if (!playerFellOff)
            {
                Move(h, v);
                timeSinceLaunched = 0;
            }
            else
            {
                playerRigidbody.position = new Vector3(0, 0, 0);
                playerFellOff = false;
            }
            
        }
        else if (launching)
        {
            FollowJumpPath(jumpPathStartingDirection);
        }
        
        Turning();
        Animating(h, v);
    }

    void FollowJumpPath(Vector3 jumpPathStartingDirection)
    {        
        float x = jumpPathStartingDirection.x;
        float z = jumpPathStartingDirection.z;
        timeSinceLaunched += Time.deltaTime;

        playerRigidbody.MovePosition(
            new Vector3(
                jumpStartingPosition.x + x * timeSinceLaunched * launchSpeed, 
                JumpPath(timeSinceLaunched) * launchSpeed,
                jumpStartingPosition.z + z * timeSinceLaunched * launchSpeed
            )
        );

        if(JumpPath(timeSinceLaunched) <= 0){
            launching = false;
            launched = false;
            timeSinceLaunched = 0;

            // make sure the player is sitting on the ground
            playerRigidbody.MovePosition(new Vector3(playerRigidbody.position.x, 0, playerRigidbody.position.z));
        }
    }

    float JumpPath(float startTime)
    {
        // can change this to any equation of a line
        float y;
        y = startTime;

        // y = Mathf.Sin(2 * startTime) + 0.5f;
        
        
        y -= 1; //  (x-1)
        y *= y; //  (x-1)^2
        y = -y; // -(x-1)^2
        y += 1.005f; // -(x-1)^2 + 1


        /*
        // was gonna try to make a shorter jump but it would break, ran out of time
        y -= 1;         //     (x-1)
        y *= y;         //     (x-1)^2
        y = -1 / 2 * y; // -1/2(x-1)^2
        y += 0.5f;      // -1/2(x-1)^2 + 0.5
        */
        return y;
    }

    void Move(float h, float v)
    {
        movement.Set(h, 0f, v);
        movement = movement.normalized * speed * Time.deltaTime;

        playerRigidbody.MovePosition(transform.position + movement);
    }

    void Turning()
    {
        Ray camRay = Camera.main.ScreenPointToRay(Input.mousePosition);

        RaycastHit floorHit;

        if (Physics.Raycast(camRay, out floorHit, camRayLength, floorMask))
        {
            Vector3 playerToMouse = floorHit.point - transform.position;
            playerToMouse.y = 0f;

            Quaternion newRotation = Quaternion.LookRotation(playerToMouse);
            playerRigidbody.MoveRotation(newRotation);
        }

    }
    void Animating(float h, float v){
        bool walking = (h != 0f || v != 0f);
        anim.SetBool("IsWalking", walking);
    }
    
}
