using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Bullet : MonoBehaviour {

    public float duration = 4f;
    public int damage = 0;

    public Vector3 velocity = Vector3.zero;
    public GameObject myCreator;
    public BattleController battleController;
    public float xPosWhenDefendClicked = 0f;
    public GameObject defendLine;
    public GameObject reflectLine;


	// Use this for initialization
	void Start () {
        defendLine = GameObject.FindGameObjectWithTag("DefendLine");
        reflectLine = GameObject.FindGameObjectWithTag("ReflectLine");
    }
	
	// Update is called once per frame
	void Update () {
        duration -= Time.deltaTime;
        if (duration <= 0f)
            Destroy(gameObject);

        transform.position += velocity * Time.deltaTime;
	}


    void OnTriggerEnter(Collider other)
    {
        if (myCreator.tag == "Player" && other.tag == "Enemy")
        {

            Battler b = other.GetComponent<Battler>();
            if (!b.IsDefending())
                b.TakeDamage(damage);

            Destroy(gameObject);
        }

        if (myCreator.tag == "Enemy" && other.tag == "Player")
        {

            Battler b = other.GetComponent<Battler>();
            float defendLinePos = defendLine.transform.position.x;
            float reflectLinePos = reflectLine.transform.position.x;

            if (!b.IsDefending())
            {
                b.TakeDamage(damage);
            }
            else
            {
                if (xPosWhenDefendClicked < defendLinePos)
                {
                    if(xPosWhenDefendClicked < reflectLinePos)
                    {
                        b.SpawnBullet(b.transform.position, b.transform.forward, 8f);
                        battleController.ShowReflectIndicator();
                    }
                }
            }
                
            Destroy(gameObject);
        }
    }


    public void SetVelocity(Vector3 v)
    {
        velocity = v;
    }
}
