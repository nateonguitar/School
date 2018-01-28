using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ShoppingAreaController : MonoBehaviour {
    Vector2 dimensions = new Vector2(128, 128);
    public GameObject cursor;
    public GameObject placement;
    Business[][] businesses;

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
        Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
        RaycastHit hit;
        if (Physics.Raycast(ray, out hit))
        {
            if (hit.transform.gameObject.tag == "Ground")
            {
                cursor.SetActive(true);
                cursor.transform.position = new Vector3(
                    hit.point.x < 0 ? (int)hit.point.x - 0.5f : (int)hit.point.x + 0.5f,
                    0.01f,
                    hit.point.z < 0 ? (int)hit.point.z - 0.5f : (int)hit.point.z + 0.5f
                );
            }
            else
            {
                cursor.SetActive(false);
            }

            if (Input.GetMouseButtonDown(0))
            {
                Instantiate(
                    placement,
                    cursor.transform.position,
                    Quaternion.Euler(90, 0, 0)
                );
            }
        }
    }
}
