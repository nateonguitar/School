using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BallBehavior : MonoBehaviour {

    public bool isActive = false;

    private Vector3 startPosition;
    private Rigidbody myRigidbody;




	// Use this for initialization
	void Start () {
        startPosition = transform.position;
        myRigidbody = GetComponent<Rigidbody>();

	}
	
	// Update is called once per frame
	void Update () {
		if (transform.position.y < -8f)
        {
            isActive = false;
            ResetPosition();
        }


        if (Input.GetMouseButtonDown(0))
        {
            if (!isActive)
            {
                isActive = true;
                StartMovement();
            }
        }
	}




    private void StartMovement()
    {
        Vector3 direction = new Vector3(Random.Range(-8f, 8f), 8f, 0f);
        myRigidbody.AddForce(direction, ForceMode.Impulse);
    }

    private void ResetPosition()
    {
        transform.position = startPosition;
        myRigidbody.velocity = Vector3.zero;
    }

    void OnCollisionEnter(Collision other)
    {
        if (other.gameObject.name == "Top")
        {
            Debug.Log(myRigidbody.velocity.y);

            if(Mathf.Abs(myRigidbody.velocity.y) < 1f)
            {
                myRigidbody.velocity = new Vector3(myRigidbody.velocity.x, myRigidbody.velocity.y - 2f, 0);
            }
        }
    }
}
