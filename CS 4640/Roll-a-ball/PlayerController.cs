using System.Collections;
using System.Collections.Generic;

using UnityEngine;
using UnityEngine.UI;

public class PlayerController : MonoBehaviour {
    public float speed;
    public Text countText;
    public Text winText;

    private Rigidbody rb;
    private int count;
    public float sizeChange;

    void Start()
    {
        rb = GetComponent<Rigidbody>();
        count = 0;
        setCountText();
        winText.text = "";
        sizeChange = 0.2f;
        //transform.position = new Vector3(10, 0.5f, -3f);
    }
    void FixedUpdate()
    {
        float moveHorizontal = Input.GetAxis("Horizontal");
        float moveVertical = Input.GetAxis("Vertical");

        Vector3 movement = new Vector3(moveHorizontal, 0.0f, moveVertical);
        rb.AddForce(movement * speed);
    }

    void OnTriggerEnter(Collider other)
    {
        if (other.gameObject.CompareTag("Pick Up"))
        {
            other.gameObject.SetActive(false);
            count++;
            setCountText();
        }
        if (other.gameObject.CompareTag("Pick Up Increase Size"))
        {
            other.gameObject.SetActive(false);
            transform.localScale += new Vector3(sizeChange, sizeChange, sizeChange);
        }

        if (other.gameObject.CompareTag("Pick Up Decrease Size"))
        {
            other.gameObject.SetActive(false);
            transform.localScale += new Vector3(-sizeChange, -sizeChange, -sizeChange);
        }
    }

    void setCountText()
    {
        countText.text = "Count: " + count.ToString();
        if(count >= 12)
        {
            winText.text = "You Win!";
        }
    }
}
