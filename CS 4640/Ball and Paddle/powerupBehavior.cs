using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class powerupBehavior : MonoBehaviour {

    private float xPosition;
    private float yPosition;
    private float zPosition;
    private Vector3 startPosition;
    private int halfOfPlayFieldWidth = 17;
    private float yOffset = 0;

	// Use this for initialization
	void Start () {
        // going to generate a random number between 0 and 1
        // then multiply that by 2 so we have a random number between 0 and 2
        // then subtract 1 so we have a random number between -1 and 1
        // then multiply that number by half of the play field's width
        // so that this power up shifts left or right.
        // this allows spawning randomly anywhere across the board.
        xPosition = (Random.value * 2 - 1) *halfOfPlayFieldWidth;
        yPosition = 20f;
        zPosition = 0;
        startPosition = new Vector3(xPosition, yPosition, zPosition);

        transform.position = startPosition;
	}
	
	// Update is called once per frame
	void Update () {
        moveDown();
        spin();
        deleteIfOffScreen();
	}

    private void moveDown()
    {
        yOffset += 0.1f;
        transform.position = new Vector3(xPosition, yPosition - yOffset, zPosition);
    }

    private void spin()
    {
        transform.Rotate(new Vector3(30, 60, 90) * Time.deltaTime);
    }

    private void deleteIfOffScreen()
    {
        if (gameObject.transform.position.y < -3)
        {
            Destroy(gameObject);
        }
    }
}
