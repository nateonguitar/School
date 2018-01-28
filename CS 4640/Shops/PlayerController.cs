using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour {

    CharacterController characterController;
    Vector3 currentPlayerPosition;
    Vector3 currentPlayerRotation;
    Vector2 previousMouseLocation;
    public GameObject ground;
    public float speed = 5;


	// Use this for initialization
	void Start () {
        characterController = GetComponent<CharacterController>();
        previousMouseLocation = Input.mousePosition;
	}

    void FixedUpdate()
    {
        handleCameraMovement();
    }

    private void handleCameraMovement()
    {
        float horizontal = Input.GetAxis("Horizontal") + Input.GetAxis("Vertical");
        float vertical = Input.GetAxis("Vertical") - Input.GetAxis("Horizontal");

        if (horizontal != 0 || vertical != 0)
        {

            Vector3 desiredDestination = new Vector3(
                gameObject.transform.position.x + horizontal * speed * Time.deltaTime,
                gameObject.transform.position.y,
                gameObject.transform.position.z + vertical * speed * Time.deltaTime
            );

            float offset = gameObject.transform.position.y;

            if (
                   desiredDestination.x <  ground.transform.localScale.x/2 - offset / 2
                && desiredDestination.x > -ground.transform.localScale.x/2 - offset / 2
                && desiredDestination.z <  ground.transform.localScale.y/2 - offset / 2
                && desiredDestination.z > -ground.transform.localScale.y/2 - offset / 2
            )
            {
                gameObject.transform.position = desiredDestination;
            }
            else
            {
                float pushBackSpeed = 1 * Time.deltaTime;
                transform.position = Vector3.MoveTowards(transform.position, Vector3.zero, pushBackSpeed);
            }

            
        }
    }
}
