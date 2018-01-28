using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DamageIndicator : MonoBehaviour {

    public float duration = 1f;

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
        duration -= Time.deltaTime;
        if (duration <= 0f)
            Destroy(gameObject);
	}
}
