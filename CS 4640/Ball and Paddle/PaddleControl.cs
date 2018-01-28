using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PaddleControl : MonoBehaviour {

    private float lastSpeedX = 0f;
    private float paddleMaxWidth = 2.5f;
    private float paddleMinWidth = 0.5f;
    

	// Use this for initialization
	void Start () {
        
	}
	
	// Update is called once per frame
	void Update () {
        float mouseX = Input.mousePosition.x;
        Vector3 mPosWorld = Camera.main.ScreenToWorldPoint(new Vector3(mouseX, 0f, 16f));

        lastSpeedX = (mPosWorld.x - transform.position.x);
        transform.Translate(new Vector3(lastSpeedX, 0f, 0f));
	}



    void OnCollisionEnter(Collision other)
    {
        if (other.gameObject.name == "Ball")
        {
            // find a number between -1 and 1 to represent where the ball hit on the paddle
            float xDirection = (gameObject.transform.position.x - other.contacts[0].point.x) / (gameObject.transform.localScale.x * 2);


            float xVelocity = 12f;
            float yVelocity = 7.5f;
            // bounces in the opposite direction if we don't flip it
            xDirection = -xDirection;

            yVelocity *= 1 - Mathf.Abs(xDirection);
            

            Rigidbody r = other.gameObject.GetComponent<Rigidbody>();
            r.velocity = Vector3.zero;
            //r.AddForce(new Vector3(direction * 10, 10f, 0f) * Time.deltaTime, ForceMode.VelocityChange);


            Vector3 direction = new Vector3(xDirection * xVelocity, yVelocity + 1f, 0f);
            r.AddForce(direction, ForceMode.Impulse);
        }
    }

    void OnTriggerEnter(Collider other)
    {
        if (other.gameObject.tag == "PowerupMakeWider")
        {
            gameObject.transform.localScale += Vector3.right / 2;

            if (gameObject.transform.localScale.x > paddleMaxWidth)
            {
                gameObject.transform.localScale = new Vector3(paddleMaxWidth, gameObject.transform.localScale.y, gameObject.transform.localScale.z);
            }
            Destroy(other.gameObject);
        }

        if (other.gameObject.tag == "PowerupMakeThinner")
        {
            gameObject.transform.localScale -= Vector3.right / 2;

            if (gameObject.transform.localScale.x < paddleMinWidth)
            {
                gameObject.transform.localScale = new Vector3(paddleMinWidth, gameObject.transform.localScale.y, gameObject.transform.localScale.z);
            }
            Destroy(other.gameObject);
        }
    }
}
