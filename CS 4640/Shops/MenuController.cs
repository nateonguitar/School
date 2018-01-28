using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MenuController : MonoBehaviour {

    private bool menuShowing = false;

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
        if (Input.GetKeyDown(KeyCode.Escape))
        {
            toggleMenu();
        }
    }

    private void toggleMenu()
    {
        if (menuShowing)
        {
            Time.timeScale = 1;
            gameObject.SetActive(true);
        }
        else
        {
            Time.timeScale = 0;
            gameObject.SetActive(true);
        }

        menuShowing = !menuShowing;
    }
}
