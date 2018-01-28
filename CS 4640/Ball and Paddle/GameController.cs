using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameController : MonoBehaviour {

    private int timer = 0;
    private int timerMax = 200;
    public GameObject powerupMakeWider;
    public GameObject powerupMakeThinner;
    public List<GameObject> powerupTypes = new List<GameObject>();

    // Use this for initialization
    void Start () {
        // add all the types of powerups to the powerupTypes List
        // this is so we can instantiate random powerups from this list
        powerupTypes.Add(powerupMakeWider);
        powerupTypes.Add(powerupMakeThinner);
	}
	
	// Update is called once per frame
	void Update () {
        spawnPowerup(Random.Range(0, powerupTypes.Count));
    }

    private void spawnPowerup(int type)
    {
        timer++;
        if(timer >= timerMax)
        {
            timer = 0;
            Instantiate<GameObject>(powerupTypes[type]);
        }
    }
}
